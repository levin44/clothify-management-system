package org.example.service;

import org.example.dao.CustomerDAO;
import org.example.entity.Customer;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerService {
    private CustomerDAO customerDAO = new CustomerDAO();

    public void addCustomer(Customer customer) {
        customerDAO.saveCustomer(customer);
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.save(customer);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw e;
//        }

    }
}

