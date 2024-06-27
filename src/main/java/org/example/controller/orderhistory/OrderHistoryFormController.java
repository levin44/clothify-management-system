package org.example.controller.orderhistory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.entity.Orders;
import org.example.service.OrderService;

import java.util.Date;

public class OrderHistoryFormController {

    @FXML
    private TableView<Orders> tblOrderHistory;
    @FXML
    private TableColumn<Orders, String> colOrderId;
    @FXML
    private TableColumn<Orders, String> colPaymentType;
    @FXML
    private TableColumn<Orders, Double> colTotalCost;
    @FXML
    private TableColumn<Orders, Date> colDate;
    @FXML
    private TableColumn<Orders, String> colEmployee;
    @FXML
    private TableColumn<Orders, String> colCustomer;

    private OrderService orderService = new OrderService();

    @FXML
    private void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        ObservableList<Orders> orderList = FXCollections.observableArrayList(orderService.getAllOrders());
        tblOrderHistory.setItems(orderList);
    }
}
