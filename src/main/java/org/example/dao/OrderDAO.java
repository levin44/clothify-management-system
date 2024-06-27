package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Orders;
import org.example.entity.Supplier;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO {

    public static boolean saveOrder(Orders orders) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(orders);
        session.getTransaction().commit();
        session.close();
        return true;

    }

    public static int getOrderCount() {
        Session session = HibernateUtil.getSession();
        Long count = (Long) session.createQuery("SELECT COUNT(o) FROM Orders o").uniqueResult();
        session.close();
        return count.intValue();
    }
    public List<Orders> getAllOrders() {
        Session session = HibernateUtil.getSession();
        Query<Orders> query = session.createQuery("FROM Orders", Orders.class);
        List<Orders> orders = query.getResultList();
        session.close();
        return orders;
    }
}
