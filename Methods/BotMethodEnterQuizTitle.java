package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Dao.QuizDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Quiz;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.EnterQuizDescription;
import static ru.telebot.State.BotState.EnterQuizTitle;




@Component
public class BotMethodEnterQuizTitle extends BotMethod {

    UserDao userDao;
    QuizDao quizDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    private final BotState botState= EnterQuizTitle;
    private final String command ="";


    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        string.substring(command.length());
        String title =string.substring(command.length());
        user.setBotState(EnterQuizDescription);
        userDao.update(user);
        Quiz quiz= new Quiz();
        quiz.setQuizName(title);
        quiz.setEditingId(user.getUserId());
        quiz.setUser(user);
        quizDao.create(quiz);
        sendMessage.setText("Enter quiz description");
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