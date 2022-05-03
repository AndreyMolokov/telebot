package ru.telebot.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.telebot.Dao.QuestionDao;
import ru.telebot.Dao.QuizDao;
import ru.telebot.Dao.UserDao;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.Quiz;
import ru.telebot.DataClass.User;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class QuizResolveCache {
    QuizDao quizDao;
    QuestionDao questionDao;
    Map<Long,List<Question>> cache;
    Map<Long,Integer> score;

    @Autowired
    public void setQuizDao(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    {
        System.out.println("Start Quiz ResolveCache");
    Map<Long,List<Question>> cache = new HashMap<>();
    Map<Long,Integer> score = new HashMap<>();
    this.cache=cache;
    this.score=score;
    }

    public Question getQuestion(User user){
        Long user_id = user.getUserId();
        Question question = new Question();
        if(cache.containsKey(user_id)){
            question = cache.get(user_id).get(0);
            cache.get(user_id).remove(0);
        }
        return question;
    }
    public boolean hasQuestion(User user){
        Long user_id = user.getUserId();
        if(cache.containsKey(user_id)){
           return !cache.get(user_id).isEmpty();
        }
        return false;
    }
    public void delete(User user){
        cache.remove(user.getUserId());
        score.remove(user.getUserId());
    }



    public void uploadQuestion(List<Question> questionList, Long user_id){
        cache.put(user_id,questionList);
        score.put(user_id,0);
    }

    public Integer getscore(User user){
        Long user_id = user.getUserId();
        Integer usercore = score.get(user_id);
        score.remove(user_id);
        return usercore;
    }


    public void upscore(User user){
        Long user_id = user.getUserId();
        score.put(user_id,getscore(user)+1);
    }


}

