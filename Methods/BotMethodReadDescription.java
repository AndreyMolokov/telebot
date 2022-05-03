package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Cache.QuizResolveCache;
import ru.telebot.Dao.AnswerDao;
import ru.telebot.Dao.QuestionDao;
import ru.telebot.Dao.QuizDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Answer;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import java.util.List;
import java.util.stream.Collectors;

import static ru.telebot.State.BotState.ReadDescription;
import static ru.telebot.State.BotState.ResolveQuiz;



@Component
public class BotMethodReadDescription extends BotMethod {

    UserDao userDao;
    QuizDao quizDao;
    QuizResolveCache quizResolveCache;
    AnswerDao answerDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Autowired
    public void setQuizResolveCache(QuizResolveCache quizResolveCache) {
        this.quizResolveCache = quizResolveCache;
    }


    private final BotState botState= ReadDescription;
    private final String command ="";


    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        user.setBotState(ResolveQuiz);
        userDao.update(user);
        Question question = quizResolveCache.getQuestion(user);
        List<Answer> answerList = answerDao.findAll(question);
        String sting = answerList.stream().map(Answer::toString).collect(Collectors.joining());
        sendMessage.setText(sting);
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