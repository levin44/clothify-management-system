package org.example.controller.order;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.example.UserContext;
import org.example.entity.*;
import org.example.service.CustomerService;
import javafx.scene.control.Alert.AlertType;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.util.EmailUtil;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderFormController {
    public JFXTextField customerNameField;
    public JFXTextField customerPhoneField;
    public JFXTextField productIdField;
    public JFXTextField quantityField;
    public Label lblProductName;
    public Label lblUnitPrice;
    public Label lblPrice;
    public TableView tblCart;
    public TableColumn colProductId;
    public TableColumn colProduct;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colPrice;
    public Label lblTotal;
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField customerEmailField;

    private CustomerService customerService = new CustomerService();
    private ProductService productService = new ProductService();
    private OrderService orderService = new OrderService();

    Product product;

    User username;
    public void initialize() {
        loadDateAndTime();
        User currentUser = UserContext.getInstance().getCurrentUser();
        username=currentUser;

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("total"));

        lblOrderId.setText(orderService.generateOrderId());

    }



    private void loadDateAndTime() {
        //Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));


        //Time
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour() + " : " + time.getMinute() + " : " + time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void handleAddCustomer(ActionEvent actionEvent) {
        try {
            String customerPhone = customerPhoneField.getText();
            String customerName = customerNameField.getText();
            String customerEmail = customerEmailField.getText();

            if (customerPhone.isEmpty() || customerName.isEmpty()) {
                showAlert(AlertType.ERROR, "Form Error!", "Please enter customer ID and name");
                return;
            }
            Customer customer = new Customer();
            customer.setPhone(Integer.parseInt(customerPhone));
            customer.setName(customerName);
            customer.setEmail(customerEmail);

            customerService.addCustomer(customer);
            showAlert(AlertType.INFORMATION, "Success", "Customer added successfully!");

            // Clear the fields after successful addition
            customerPhoneField.clear();
            customerNameField.clear();
            customerEmailField.clear();

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to add customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void loadProductDetails(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(productIdField.getText());
            product = productService.getProductById(id);

            if (product != null) {
                lblProductName.setText(product.getName());
                lblUnitPrice.setText(String.valueOf(product.getPrice()));

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Product not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load supplier details: " + e.getMessage());
            e.printStackTrace();
        }
    }
    ObservableList<CartTbl> cartList = FXCollections.observableArrayList();

    public void btnAddToCart(ActionEvent actionEvent) {
        int productId = Integer.parseInt(productIdField.getText());
        String productName = product.getName();
        Double unitPrice = product.getPrice();
        int qty = Integer.parseInt(quantityField.getText());
        Double totalPrice =qty*unitPrice;
        CartTbl cartTbl = new CartTbl(productId,productName,unitPrice,qty,totalPrice);

        cartList.add(cartTbl);
        tblCart.setItems(cartList);
        calcNetTotal();
    }

    public void loadPrice(ActionEvent actionEvent) {
        int qty = Integer.parseInt(quantityField.getText());
        lblPrice.setText(String.valueOf(product.getPrice() * qty));
    }

    public void calcNetTotal() {
        double ttl = 0;
        for (CartTbl cartObj : cartList) {
            ttl += cartObj.getTotal();
        }
        lblTotal.setText(String.valueOf(ttl) + "/=");
    }

    public void btnPlaceOrder(ActionEvent actionEvent) {

        try {
            String orderId = lblOrderId.getText();
            String customerPhone = customerPhoneField.getText();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());

            if (customerPhone.isEmpty() || cartList.isEmpty()) {
                showAlert(AlertType.ERROR, "Form Error!", "Please fill in customer details and add products to the cart");
                return;
            }

            Customer customer = customerService.getCustomerByPhone(Integer.parseInt(customerPhone));
            if (customer == null) {
                showAlert(AlertType.ERROR, "Error", "Customer not found");
                return;
            }

            Orders order = new Orders();
            order.setOrderDate(orderDate);
            order.setOrderId(orderId);
            order.setCustomer(customer);
            order.setUser(username);
            order.setPaymentType("Credit Card"); // This is an example, set the appropriate payment type
            order.setTotalCost(cartList.stream().mapToDouble(CartTbl::getTotal).sum());

            List<OrderDetails> orderDetailsList = cartList.stream().map(cartItem -> {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setProduct(productService.getProductById(cartItem.getProductId()));
                orderDetails.setQuantity(cartItem.getQty());
                orderDetails.setOrder(order);
                return orderDetails;
            }).collect(Collectors.toList());

            order.setOrderDetails(orderDetailsList);

            orderService.addOrder(order);

            showAlert(AlertType.INFORMATION, "Success", "Order placed successfully!");

            // Clear the fields and the cart after successful order placement
            lblOrderId.setText(orderService.generateOrderId());
            customerPhoneField.clear();
            customerNameField.clear();
            productIdField.clear();
            quantityField.clear();
            lblProductName.setText("");
            lblUnitPrice.setText("");
            lblPrice.setText("");
            lblTotal.setText("");
            cartList.clear();
            tblCart.setItems(cartList);
            System.out.println("this is order");
            System.out.println(order);
            sendEmail(customer.getEmail(), order);

        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to place order: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void sendEmail(String customerEmail, Orders order) {
        String subject = "Order Confirmation - " + order.getOrderId();
        StringBuilder body = new StringBuilder();
        body.append("Dear ").append(order.getCustomer().getName()).append(",\n\n");
        body.append("Thank you for your order! Here are the details:\n\n");
        body.append("Order ID: ").append(order.getOrderId()).append("\n");
        body.append("Order Date: ").append(new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDate())).append("\n\n");
        body.append("Items:\n");

        for (OrderDetails detail : order.getOrderDetails()) {
            body.append(detail.getProduct().getName())
                    .append(" - Quantity: ").append(detail.getQuantity())
                    .append(" - Price: ").append(detail.getProduct().getPrice())
                    .append(" - Total: ").append(detail.getQuantity() * detail.getProduct().getPrice())
                    .append("\n");
        }

        body.append("\nTotal Cost: ").append(order.getTotalCost()).append("\n\n");
        body.append("Thank you for shopping with us!\n");
        body.append("Best regards,\nClothify");

        EmailUtil.sendEmail(customerEmail, subject, body.toString());
    }

    public void loadCustomer(ActionEvent actionEvent) {
        try {
            int phone = Integer.parseInt(customerPhoneField.getText());
            Customer customer = customerService.getCustomerByPhone(phone);

            if (customer != null) {
                customerNameField.setText(customer.getName());
                customerEmailField.setText(customer.getEmail());

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Customer not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load supplier details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
