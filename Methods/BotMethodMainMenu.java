package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.telebot.State.BotState.*;



@Component
public class BotMethodMainMenu extends BotMethod{

    UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private final BotState botState= MainMenu;
    private final String command ="";


    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        String semicommand =string.substring(command.length());
        if(semicommand.equals("cq")){
            user.setBotState(EnterQuizTitle);
            userDao.update(user);
            sendMessage.setText("Enter quiz title");
            return sendMessage;
        }
        if(semicommand.equals("sq")){
            user.setBotState(SearchQuiz);
            userDao.update(user);
            sendMessage.setText("enter username or quiz name");
            return sendMessage;
        }

        sendMessage.setText("search or create quiz?");
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
