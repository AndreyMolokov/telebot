package ru.telebot.Dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.telebot.Configs.HibernateConfig;
import ru.telebot.DataClass.User;

import java.util.List;

@Component
public class UserDao  {
    SessionFactory localSessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.localSessionFactory = sessionFactory;
    }

    public void create(User user){

        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();

    }
    public User readbyid(Long id) {
        Session session = localSessionFactory.openSession();
        User user =  session.get(User.class, id);
        session.close();
        return user;
    }
    public User readbychatid(Long chatid) {
        Session session=localSessionFactory.openSession();
        List<User> userList=session.createQuery("from User where chatTelId =:chatid ")
                .setParameter("chatid", chatid).list();
        session.close();

        if(userList.isEmpty()){
            System.out.println("Пустой лист");
            return null;}
        return userList.get(0);
    }
    public void update(User user) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();

    }
    public boolean hasnick(String nick){
        Session session=localSessionFactory.openSession();
        List<User> userList=session.createQuery("from User where userBotNick =:nick ")
                .setParameter("nick", nick).list();
        session.close();
         if(userList.isEmpty())return false;
         return true;

    }
    public User findbynick(String nick){
        Session session=localSessionFactory.openSession();
        List<User> userList=session.createQuery("from User where userBotNick =:nick ")
                .setParameter("nick", nick).list();
        session.close();
        if(userList.isEmpty())return null;
        return userList.get(0);

    }
    public void delete(User user) {
        Session session = localSessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }
}
