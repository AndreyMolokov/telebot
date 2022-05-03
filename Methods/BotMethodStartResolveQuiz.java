package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Cache.QuizResolveCache;
import ru.telebot.Dao.QuizDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.ResolveQuiz;
import static ru.telebot.State.BotState.StartResolveQuiz;


@Component
public class BotMethodStartResolveQuiz extends BotMethod{

    BotState botState =  StartResolveQuiz;
    String command = "";


    private UserDao userDao;
    private QuizResolveCache quizResolveCache;

    @Autowired
    public void setQuizResolveCache(QuizResolveCache quizResolveCache) {
        this.quizResolveCache = quizResolveCache;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        try {
        user.setBotState(ResolveQuiz);

        userDao.update(user);
        Question question = quizResolveCache.getQuestion(user);
        sendMessage.setText("START"+question.toString());
        return sendMessage;
        }catch (Exception e){
            e.printStackTrace();
        }
        sendMessage.setText("Exception");
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
