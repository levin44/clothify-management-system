package org.example.controller.order;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.entity.CartTbl;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.service.CustomerService;
import javafx.scene.control.Alert.AlertType;
import org.example.service.ProductService;

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

    private CustomerService customerService = new CustomerService();
    private ProductService productService = new ProductService();

    Product product;

    public void handleAddCustomer(ActionEvent actionEvent) {
        try {
            String customerPhone = customerPhoneField.getText();
            String customerName = customerNameField.getText();

            if (customerPhone.isEmpty() || customerName.isEmpty()) {
                showAlert(AlertType.ERROR, "Form Error!", "Please enter customer ID and name");
                return;
            }
            Customer customer = new Customer();
            customer.setPhone(Integer.parseInt(customerPhone));
            customer.setName(customerName);
            System.out.println(customer);

            customerService.addCustomer(customer);
            showAlert(AlertType.INFORMATION, "Success", "Customer added successfully!");

            // Clear the fields after successful addition
            customerPhoneField.clear();
            customerNameField.clear();

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
    }

    public void loadPrice(ActionEvent actionEvent) {
        int qty = Integer.parseInt(quantityField.getText());
        lblPrice.setText(String.valueOf(product.getPrice() * qty));
    }

}
