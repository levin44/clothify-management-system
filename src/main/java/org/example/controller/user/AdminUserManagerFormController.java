package org.example.controller.user;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.entity.Supplier;
import org.example.entity.User;
import org.example.service.UserService;

import java.util.List;

public class AdminUserManagerFormController {
    public TableView tblProduct;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colRole;
    public JFXTextField emailField;
    public JFXTextField updateUserIdField;
    public JFXTextField updateEmailField;
    public JFXTextField updateNameField;
    public MenuButton registerRole;
    public MenuItem menuItemAdmin;
    public MenuItem menuItemEmployee;
    public MenuButton updateRole;
    public JFXTextField removeUserIdField;
    public MenuItem menuItemUpdateAdmin;
    public MenuItem menuItemUpdateEmployee;
    public TableView tblUser;
    private UserService userService = new UserService();
    @FXML
    public void initialize() {
        //register
        menuItemAdmin.setOnAction(event -> registerRole.setText(menuItemAdmin.getText()));
        menuItemEmployee.setOnAction(event -> registerRole.setText(menuItemEmployee.getText()));
        //update
        menuItemUpdateAdmin.setOnAction(event -> updateRole.setText(menuItemUpdateAdmin.getText()));
        menuItemUpdateEmployee.setOnAction(event -> updateRole.setText(menuItemUpdateEmployee.getText()));
//table
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUserTable();
    }


    private void loadUserTable() {
        List<User> users = userService.getAllUsers();
        ObservableList<User> userList = FXCollections.observableArrayList(users);
        tblUser.setItems(userList);
    }

    public void handleRegister(ActionEvent actionEvent) {
        try {
            String email = emailField.getText();
            String role = registerRole.getText();

            if (email.isEmpty() || role.equals("Select Role")) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter email and select a role");
                return;
            }

            User user = new User();
            user.setEmail(email);
            user.setRole(role);

            userService.addUser(user);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");

            // Clear the fields after successful registration
            emailField.clear();
            registerRole.setText("Select Role");
            loadUserTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void handleUpdateUser(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(updateUserIdField.getText());
            String name = updateNameField.getText();
            String role = updateRole.getText();
            String email = updateEmailField.getText();

            if (name.isEmpty() || role.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields");
                return;
            }

            User user = new User();
            user.setUserId(id);
            user.setName(name);
            user.setRole(role);
            user.setEmail(email);

            userService.updateUser(user);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");

            // Clear the fields after successful update
            updateUserIdField.clear();
            updateNameField.clear();
            updateRole.setText("Select Role");
            updateEmailField.clear();
            loadUserTable();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRemoveUser(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(removeUserIdField.getText());

            boolean isDeleted = userService.deleteUserById(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User removed successfully!");
                removeUserIdField.clear();
                loadUserTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadUserDetails(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(updateUserIdField.getText());
            User user = userService.getUserById(id);

            if (user != null) {
                updateNameField.setText(user.getName());
                updateEmailField.setText(user.getEmail());
                updateRole.setText(user.getRole());

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load user details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
