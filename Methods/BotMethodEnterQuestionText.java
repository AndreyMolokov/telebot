package ru.telebot.Methods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.Dao.QuestionDao;
import ru.telebot.Dao.QuizDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import static ru.telebot.State.BotState.*;





@Component
public class BotMethodEnterQuestionText extends BotMethod{

    UserDao userDao;
    QuizDao quizDao;
    QuestionDao questionDao;

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

    private final BotState botState= EnterQuestionText;
    private final String command ="";


    @Override
    public BotApiMethod response(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        String string = message.getText();
        string.substring(command.length());
        String questiontext = string.substring(command.length());
        user.setBotState(AnswerCorrectOrNot);

        Question question = new Question();
        question.setQuiz(quizDao.findCurentEditing(user));
        question.setQuestionText(questiontext);
        question.setEditingId(user.getUserId());
        questionDao.create(question);
        userDao.update(user);
        sendMessage.setText("Question saved," +
                "Is enter answer  correct?");
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
