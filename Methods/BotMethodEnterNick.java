package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.MainMenu;
import static ru.telebot.State.BotState.EnterNick;

@Component
public class BotMethodEnterNick extends BotMethod{

    UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private final BotState botState= EnterNick;
    private final String command ="";


    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        String nick =string.substring(command.length());
        System.out.println("Nick is "+ nick);
        if(userDao.hasnick(nick)){
            userDao.update(user);
            sendMessage.setText("Nick is already exist");
            return sendMessage;
        }
        user.setBotState(MainMenu);
        user.setUserBotNick(nick);
        userDao.update(user);
        sendMessage.setText("Nick is saved,search or create quiz?");
        return sendMessage;
    }

    @Override
    public BotState getstate() {
        return botState;
    }

    @Override
    public String getCommand() {
        return command;
    }


}
