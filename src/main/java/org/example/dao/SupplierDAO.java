package org.example.dao;

import org.example.entity.Supplier;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;


public class SupplierDAO {

    public boolean saveSupplier(Supplier supplier) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(supplier);
        session.getTransaction().commit();
        session.close();
        return true;

    }

    public boolean delete(String id) {
        return false;
    }
    // Other CRUD methods...

    public Supplier getSupplierById(int id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Supplier supplier = session.get(Supplier.class, id);
        session.getTransaction().commit();
        session.close();
        return supplier;
    }

    public boolean updateSupplier(Supplier supplier) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.update(supplier);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean deleteSupplierById(int id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Supplier supplier = session.get(Supplier.class, id);
        if (supplier != null) {
            session.delete(supplier);
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            session.getTransaction().rollback();
            session.close();
            return false;
        }
    }
    public List<Supplier> getAllSuppliers() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<Supplier> suppliers = session.createQuery("from Supplier", Supplier.class).list();
        session.getTransaction().commit();
        session.close();
        return suppliers;
    }
}

