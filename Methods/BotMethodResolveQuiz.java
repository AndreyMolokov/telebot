package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.telebot.Cache.QuizResolveCache;
import ru.telebot.Dao.AnswerDao;
import ru.telebot.Dao.QuizDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Answer;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.Quiz;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.telebot.State.BotState.*;




@Component
public class BotMethodResolveQuiz extends BotMethod {

    UserDao userDao;
    AnswerDao answerDao;
    QuizResolveCache quizResolveCache;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Autowired
    public void setQuizResolveCache(QuizResolveCache quizResolveCache) {
        this.quizResolveCache = quizResolveCache;
    }

    private final BotState botState= ResolveQuiz;
    private final String command ="";


    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        String ans = string.substring(1);
        try {


        if(string.charAt(0)=='a'){
            if(answerDao.read(Long.parseLong(ans)).isCorrect()){
                quizResolveCache.upscore(user);
            }
        }
        }catch (Exception e){
            e.printStackTrace();
        }


        if(!quizResolveCache.hasQuestion(user)){
            user.setBotState(MainMenu);
            userDao.update(user);
            sendMessage.setText("you score is "+quizResolveCache.getscore(user)+"search our create new quiz?");
            quizResolveCache.delete(user);
        }
        else {
            Question question = quizResolveCache.getQuestion(user);
            System.out.println(question);
            sendMessage.setText(question.toString());
        }



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
