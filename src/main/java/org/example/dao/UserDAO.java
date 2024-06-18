package org.example.dao;

import org.example.entity.Supplier;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {

    public void saveUser(User user) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public User getUserById(int id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        User user = session.get(User.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }
    public boolean updateUser(User user) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.update(user);
        session.getTransaction().commit();
        session.close();
        return true;
    }
    public boolean deleteUserById(int id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            session.getTransaction().rollback();
            session.close();
            return false;
        }
    }
    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<User> users = session.createQuery("from User", User.class).list();
        session.getTransaction().commit();
        session.close();
        return users;
    }
    public User getUserByEmail(String email) {
        Session session = HibernateUtil.getSession();
        User user = null;
        try {
            String hql = "FROM User WHERE email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            user = query.uniqueResult();
        } finally {
            session.close();
        }
        return user;
    }
}
