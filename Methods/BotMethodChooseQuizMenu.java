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
import ru.telebot.DataClass.Quiz;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.*;



@Component
public class BotMethodChooseQuizMenu extends BotMethod{

    private UserDao userDao;
    private QuizDao quizDao;
    private QuizResolveCache quizResolveCache;

    @Autowired
    public void setQuizResolveCache(QuizResolveCache quizResolveCache) {
        this.quizResolveCache = quizResolveCache;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    private final BotState botState=ChooseQuizMenu;
    private final String command ="";


    @Override
    public BotApiMethod response(User user, Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        String quizId = string.substring(1);
        try {
            Long quizIdLong = Long.parseLong(quizId);
            System.out.println("quizId = "+quizId);


        if(string.charAt(0)=='q'){
            user.setBotState(StartResolveQuiz);
            userDao.update(user);
            Quiz quiz = quizDao.read(quizIdLong);
            System.out.println(quiz.getQuestionList());
            quizResolveCache.uploadQuestion(quiz.getQuestionList(),user.getUserId());
            sendMessage.setText("Name: "+quiz.getQuizName()+"\n"+"Description: "+quiz.getQuizDescription());
            System.out.println(quizResolveCache.getQuestion(user));
            return sendMessage;
        }
        if(string.charAt(0)=='e'){
            user.setBotState(ChooseQuizMenu);
            userDao.update(user);
            sendMessage.setText("search or create quiz?");
            return sendMessage;
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        sendMessage.setText("enter q+quiz_id or e");
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

