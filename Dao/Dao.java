package ru.telebot.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class Dao<T> {
    SessionFactory localSessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.localSessionFactory = sessionFactory;
    }



    public void update(T t){

        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(t);
        tx1.commit();
        session.close();
    }

    public void create(T t) {
        System.out.println("localSessionFactory="+localSessionFactory);
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(t);
        tx1.commit();
        session.close();
    }
    public void delete(T t){
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(t);
        tx1.commit();
        session.close();
    }
}
