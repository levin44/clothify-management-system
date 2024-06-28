package org.example.service;

import org.example.dao.OrderDAO;
import org.example.entity.Orders;


import java.sql.Date;
import java.util.List;

public class SalesReportService {
    private OrderDAO orderDAO = new OrderDAO();

    public List<Orders> getDailySales(Date date) {
        return orderDAO.getOrdersByDate(date, date);
    }

    public List<Orders> getMonthlySales(Date startDate, Date endDate) {
        return orderDAO.getOrdersByDate(startDate, endDate);
    }

    public List<Orders> getAnnualSales(Date startDate, Date endDate) {
        return orderDAO.getOrdersByDate(startDate, endDate);
    }
}
