package org.example.dao;

import org.example.entity.Product;
import org.example.entity.Product;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {
    public boolean saveProduct(Product product) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(product);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public List<Product> getAllProducts() {
        Session session = HibernateUtil.getSession();
        Query<Product> query = session.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.supplier", Product.class);
        List<Product> products = query.list();
        session.close();
        return products;
    }

    public boolean updateProduct(Product product) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.update(product);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Product getProductById(int id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Product product = session.get(Product.class, id);
        session.getTransaction().commit();
        session.close();
        return product;
    }

    public boolean deleteProductById(int id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Product product = session.get(Product.class, id);
        if (product != null) {
            session.delete(product);
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            session.getTransaction().rollback();
            session.close();
            return false;
        }
    }
}
