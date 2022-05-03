package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.Begin;
import static ru.telebot.State.BotState.EnterNick;

@Component
public class BotMethodStart extends BotMethod{

    UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private final BotState botState= Begin;
    private final String command ="/start";
    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        sendMessage.setText("Enter your nick");
        user.setBotState(EnterNick);
        userDao.create(user);
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
