package org.example.auth;

import com.jfoenix.controls.JFXButton;
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

public class LoginFormController {
    public Hyperlink forgotPassword;
    public Hyperlink createAccount;
    public JFXButton loginButton;
    public JFXTextField emailField;
    public JFXTextField passwordField;
    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        forgotPassword.setOnAction(this::handleHyperlinkClick);
        createAccount.setOnAction(this::handleCreateAccount);

    }

    private void handleHyperlinkClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auth/ForgotPassword.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) forgotPassword.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCreateAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auth/CreateAccount.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) createAccount.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleLogin(ActionEvent actionEvent) {
        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
//            Parent root = loader.load();
//            Stage stage = (Stage) loginButton.getScene().getWindow();
//            stage.setScene(new Scene(root));

            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter email and password");
                return;
            }

            User user = userService.getUserByEmail(email);

            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "No user found with the provided email");
                return;
            }

            if (!BCrypt.checkpw(password, user.getPassword())) {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid password");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + user.getEmail());
            if (user.getRole().equals("admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeDashboard.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
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
