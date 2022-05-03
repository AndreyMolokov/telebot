package ru.telebot.Handler;



import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Cache.MethodCache;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.User;
import ru.telebot.Methods.BotMethod;
import ru.telebot.Methods.BotMethodHelp;
import ru.telebot.State.BotState;

import java.util.HashMap;
import java.util.Map;

import static ru.telebot.State.BotState.Begin;
import static ru.telebot.State.BotState.Help;


@Component
public class Handler {
    MethodCache methodCache;
    UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setMethodCache(MethodCache methodCache) {
        this.methodCache = methodCache;
    }

    public BotApiMethod handle(User user, Message message){


        try {
            if(userDao.readbychatid(user.getChatTelId())==null){
                user.setBotState(Begin);
            }else {
                user=userDao.readbychatid(user.getChatTelId());
            }
        }catch (NullPointerException e){
        }
        if(message.getText().substring(0,5).equals("/help"))
            return methodCache.getBotMethodCommand(Help).response(user,message);


        System.out.println("State"+user.getBotState());
        return methodCache.getBotMethodCommand(user.getBotState()).response(user, message);
    }

}
//  ChooseTitle,
//    ChooseQuestion,
//    ChooseAnswer,
//    ResolveQuiz,