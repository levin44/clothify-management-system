package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.Orders;
import org.example.entity.Supplier;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

public class OrderDAO {
//    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
//
//    public void save(Orders order) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(order);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

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
}
