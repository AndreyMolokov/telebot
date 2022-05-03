package ru.telebot.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.telebot.Configs.HibernateConfig;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.Quiz;
import ru.telebot.DataClass.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class QuestionDao {
    SessionFactory localSessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.localSessionFactory = sessionFactory;
    }
    public void create(Question question){
        Session session = localSessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        session.save(question);
        tr.commit();
        session.close();
    }
    public Question read(Long id) {
        Session session = localSessionFactory.openSession();
        Question question =session.get(Question.class,id);
        session.close();
        return question;
    }
    public List<Question> findAll(Long quiz_id) {
        Session session = localSessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
        Root<Question> root = criteriaQuery.from(Question.class);
        List<Question> questionList = session.createQuery(criteriaQuery.select(root).
                where(builder.equal(root.get("quiz"),quiz_id))).list();
        session.close();
        return questionList;
    }
    public Question findCurentEditing(User user) {
        Long userid=user.getUserId();
        Session session = localSessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
        Root<Question> root = criteriaQuery.from(Question.class);
        Question question = session.createQuery(criteriaQuery.select(root).
                where(builder.equal(root.get("editingId"),userid))).uniqueResult();
        session.close();
        return question;
    }
    public void update(Question question) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(question);
        tx1.commit();
        session.close();
    }
    public void delete(Question question) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(question);
        tx1.commit();
        session.close();
    }
}
