package org.example.service;

import org.example.entity.Orders;
import org.example.dao.OrderDAO;
import org.example.entity.Supplier;

import java.util.Date;
import java.util.List;

public class OrderService {
    private OrderDAO orderRepository = new OrderDAO();
    private OrderDAO orderDAO = new OrderDAO();


    public void addOrder(Orders orders) {OrderDAO.saveOrder(orders);
    }

    public String generateOrderId() {
        int count = OrderDAO.getOrderCount() + 1; // Assuming you have a method to get the total count of orders
        return String.format("A%03d", count);
    }

    public List<Orders> getAllOrders() {
        return orderDAO.getAllOrders();
    }



}
