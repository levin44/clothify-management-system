package org.example.dao;


import org.example.entity.Customer;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


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

    public Customer getCustomerByPhone(int phone) {
        Session session = HibernateUtil.getSession();
        Customer customer = null;
        try {
            String hql = "FROM Customer WHERE phone = :phone";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("phone", phone);
            customer = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customer;
    }
}

