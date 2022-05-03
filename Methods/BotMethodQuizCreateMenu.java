package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Dao.AnswerDao;
import ru.telebot.Dao.QuestionDao;
import ru.telebot.Dao.QuizDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.Quiz;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.*;



@Component
public class BotMethodQuizCreateMenu extends BotMethod {

    UserDao userDao;
    QuizDao quizDao;
    QuestionDao questionDao;
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
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }


    private final BotState botState = QuizCreateMenu;
    private final String command = "";

    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        if (string.charAt(0)=='q') {
            user.setBotState(EnterQuestionText);
            Question oldqusetion = questionDao.findCurentEditing(user);
            oldqusetion.setEditingId(0L);
            questionDao.update(oldqusetion);
            userDao.update(user);
            sendMessage.setText("Enter question name");
            return sendMessage;
        }
        if (string.charAt(0)=='a') {
            user.setBotState(AnswerCorrectOrNot);
            userDao.update(user);
            sendMessage.setText("Correct or Not");
            return sendMessage;
        }
        if (string.charAt(0)=='m') {
            user.setBotState(MainMenu);
            Question question =questionDao.findCurentEditing(user);
            question.setEditingId(0L);
            Quiz quiz =quizDao.findCurentEditing(user);
            quiz.setEditingId(0L);
            questionDao.update(question);
            quizDao.update(quiz);
            userDao.update(user);
            sendMessage.setText("search our create new quiz?");
            return sendMessage;}
        sendMessage.setText("Error");
        return sendMessage;
    }


        @Override
        public BotState getstate () {
            return botState;
        }

        @Override
        public String getCommand () {
            return command;
        }



}