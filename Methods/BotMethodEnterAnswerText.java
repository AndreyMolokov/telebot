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
import ru.telebot.DataClass.Answer;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.*;


@Component
public class BotMethodEnterAnswerText extends BotMethod{

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

    private final BotState botState= EnterAnswerText;
    private final String command ="";

    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        String anstext =string.substring(command.length());
        Answer answer = answerDao.findCurentEditing(user);
        answer.setAnsText(anstext);
        answer.setEditingId(0l);
        user.setBotState(QuizCreateMenu);
        answerDao.update(answer);
        userDao.update(user);
        sendMessage.setText("Answer is saved,answer,question or end");
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
