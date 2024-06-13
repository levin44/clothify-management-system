package org.example.controller.order;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.example.entity.Customer;
import org.example.service.CustomerService;
import javafx.scene.control.Alert.AlertType;

public class OrderFormController {
    public JFXTextField customerNameField;
    public JFXTextField customerPhoneField;

    private CustomerService customerService = new CustomerService();

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
}
