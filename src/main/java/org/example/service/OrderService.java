package org.example.service;

import org.example.entity.Orders;
import org.example.dao.OrderDAO;
import org.example.entity.Supplier;

public class OrderService {
    private OrderDAO orderRepository = new OrderDAO();

//    public void placeOrder(Orders order) {
//        orderRepository.save(order);
//    }
    public void addOrder(Orders orders) {OrderDAO.saveOrder(orders);
    }

}
