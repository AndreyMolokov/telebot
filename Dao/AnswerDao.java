package ru.telebot.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.telebot.Configs.HibernateConfig;
import ru.telebot.DataClass.Answer;
import ru.telebot.DataClass.Question;
import ru.telebot.DataClass.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class AnswerDao extends Dao<Answer> {
    SessionFactory localSessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.localSessionFactory = sessionFactory;
    }

    public void create(Answer answer){
        Session session = localSessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        session.save(answer);
        tr.commit();
        session.close();
    }
    public Answer read(Long id) {
        Session session = localSessionFactory.openSession();
        Answer answer =session.get(Answer.class,id);
        session.close();
        return answer;
    }
    public List<Answer> findAll(Question question) {
        Session session = localSessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Answer> criteriaQuery = builder.createQuery(Answer.class);
        Root<Answer> root = criteriaQuery.from(Answer.class);
        List<Answer> answerList = session.createQuery(criteriaQuery.select(root).
                where(builder.equal(root.get("question"),question.getQuestionId()))).list();
        session.close();
        return answerList;
    }
    public Answer findCurentEditing(User user) {
        Long userid=user.getUserId();
        Session session = localSessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Answer> criteriaQuery = builder.createQuery(Answer.class);
        Root<Answer> root = criteriaQuery.from(Answer.class);
        Answer answer = session.createQuery(criteriaQuery.select(root).
                where(builder.equal(root.get("editingId"),userid))).uniqueResult();
        session.close();
        return answer;
    }
    public void update(Answer answer) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(answer);
        tx1.commit();
        session.close();
    }
    public void delete(Answer answer) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(answer);
        tx1.commit();
        session.close();
    }
}
