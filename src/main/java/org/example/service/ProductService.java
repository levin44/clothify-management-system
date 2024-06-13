package org.example.service;

import org.example.dao.ProductDAO;
import org.example.entity.Product;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product product) {
        productDAO.saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
}
