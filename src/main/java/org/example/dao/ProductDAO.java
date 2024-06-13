package org.example.dao;

import org.example.entity.Product;
import org.example.entity.Supplier;
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


}
