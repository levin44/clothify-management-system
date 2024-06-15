package org.example.service;

import org.example.dao.ProductDAO;
import org.example.entity.Product;
import org.example.entity.Supplier;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product product) {
        productDAO.saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }
    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }
    public boolean deleteProductById(int id) {
        return productDAO.deleteProductById(id);
    }
}
