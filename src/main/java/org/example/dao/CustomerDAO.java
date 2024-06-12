package org.example.dao;


import org.example.entity.Customer;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class CustomerDAO {

    public boolean saveCustomer(Customer customer) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(customer);
        session.getTransaction().commit();
        session.close();
        return true;

    }

    public boolean delete(String id) {
        return false;
    }
    // Other CRUD methods...
}

