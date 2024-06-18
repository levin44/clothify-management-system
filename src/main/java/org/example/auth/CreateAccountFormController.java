package org.example.auth;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.entity.User;
import org.example.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountFormController {
    public Hyperlink returnToLogin;
    public JFXTextField emailField;
    public JFXTextField nameField;
    public JFXTextField passwordField;
    public JFXTextField confirmPasswordField;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        returnToLogin.setOnAction(this::handleHyperlinkClick);
    }
    private void handleHyperlinkClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auth/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnToLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCreateAccount(ActionEvent actionEvent) {
        try {
            String email = emailField.getText();
            String name = nameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

//            if (email.isEmpty()) {
//                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter an email");
//                return;
//            }

            User user = userService.getUserByEmail(email);

            if (user != null) {
               if (user.getPassword() != null){
                   showAlert(Alert.AlertType.ERROR, "User exist", "Please use forgot password to reset your password");
               }
               if (password.equals(confirmPassword)){

                   String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                   User usercreate = new User();

                   usercreate.setUserId(user.getUserId());
                   usercreate.setName(name);
                   usercreate.setPassword(hashedPassword);
                   usercreate.setEmail(email);
                   usercreate.setRole(user.getRole());

                   userService.updateUser(usercreate);
                   showAlert(Alert.AlertType.INFORMATION, "Success", "User created successfully!");

                   // Clear the fields after successful update
                   emailField.clear();
                   nameField.clear();
                   passwordField.clear();
                   confirmPasswordField.clear();
               }else {
                   showAlert(Alert.AlertType.ERROR, "Password does not match", "Please re enter passwords");
               }
            } else {
                showAlert(Alert.AlertType.ERROR, "User Not Found", "Please contact Admin to create account");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to get user: " + e.getMessage());
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
}
