package ru.telebot.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.telebot.Configs.HibernateConfig;
import ru.telebot.DataClass.Quiz;
import ru.telebot.DataClass.User;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.List;


@Component
public class QuizDao {
    SessionFactory localSessionFactory;
    @Autowired
    public void ListSessionFactory(SessionFactory sessionFactory) {
        this.localSessionFactory = sessionFactory;
    }
    public void create(Quiz quiz){
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(quiz);
        tx1.commit();
        session.close();
    }

    public Quiz read(Long id) {

        Session session = localSessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Quiz> query = criteriaBuilder.createQuery(Quiz.class);
        Root<Quiz> root = query.from(Quiz.class);
        root.join("questionList", JoinType.LEFT).join("answerList",JoinType.LEFT);
        query.select(root).where(criteriaBuilder.equal(root.get("quizId"),id));
        TypedQuery<Quiz> typedQuery = session.createQuery(query);
        Quiz quiz = typedQuery.getSingleResult();
        quiz=new Quiz(quiz);
        session.close();
        return quiz;
    }
    public List<Quiz> findByUserIdAll(Long userid) {
        Session session = localSessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        root.join("quizList", JoinType.LEFT);
        List<Quiz> quizList = session.createQuery(criteriaQuery.select(root).
                where(builder.equal(root.get("userId"),userid))).uniqueResult().getQuizList();
        session.close();

        return quizList;
    }
    public List<Quiz> findByUserNick(String userBotNick) {
        Session session = localSessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        root.fetch("quizList", JoinType.LEFT);
        try {
            User user = session.createQuery(criteriaQuery.select(root).
                    where(builder.equal(root.get("userBotNick"),userBotNick))).uniqueResult();
            session.close();
            if(user==null){
                System.out.println("User = null");
            return new ArrayList<Quiz>();
            }
            else{
                if(user.getQuizList()==null) {
                    System.out.println("Quiz = null");
                    return new ArrayList<Quiz>();
                }
            }
            return user.getQuizList();
        }catch (NullPointerException e){
            session.close();
            return null;
        }




    }


    public Quiz findCurentEditing(User user) {
        Long userid=user.getUserId();
        Session session = localSessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Quiz> criteriaQuery = builder.createQuery(Quiz.class);
        Root<Quiz> root = criteriaQuery.from(Quiz.class);
        Quiz quiz = session.createQuery(criteriaQuery.select(root).
                where(builder.equal(root.get("editingId"),userid))).uniqueResult();
        session.close();
        return quiz;

    }

    public List<Quiz> findByName(String quiz_name) {
        Session session = localSessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Quiz> query = cb.createQuery(Quiz.class);
        Root<Quiz> root = query.from(Quiz.class);
        root.fetch("user");
        query.select(root);
        query.where(cb.equal(root.get("quizName"),quiz_name));
        List<Quiz> quizList = session.createQuery(query).list();
        session.close();
        if(quizList==null)
            return new ArrayList<Quiz>();
        return quizList;
    }
    public void update(Quiz quiz) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(quiz);
        tx1.commit();
        session.close();
    }
    public void delete(Quiz quiz) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(quiz);
        tx1.commit();
        session.close();
    }
}
