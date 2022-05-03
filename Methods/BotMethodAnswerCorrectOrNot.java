package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Dao.AnswerDao;
import ru.telebot.Dao.QuestionDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Answer;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.*;

@Component
public class BotMethodAnswerCorrectOrNot extends BotMethod{

    UserDao userDao;
    QuestionDao questionDao;
    AnswerDao answerDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setAnswerDao(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }
    @Autowired
    public void setAnswerDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    private final BotState botState= AnswerCorrectOrNot;
    private final String command ="";

    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        String usermessage =string.substring(command.length());
        Answer answer = new Answer();
        answer.setQuestion(questionDao.findCurentEditing(user));
        answer.setEditingId(user.getUserId());

        if(usermessage.charAt(0)=='y' || usermessage.charAt(0)=='n'){
            user.setBotState(EnterAnswerText);
            if(usermessage.charAt(0)=='n'){
                answer.setCorrect(false);
            }else {
                answer.setCorrect(true);
            }
            userDao.update(user);
            answerDao.create(answer);
            sendMessage.setText("enter answer text");
            return sendMessage;
        }
        sendMessage.setText("yes or no");
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
