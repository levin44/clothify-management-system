package org.example.auth;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public Hyperlink forgotPassword;
    public Hyperlink createAccount;
    public JFXButton loginButton;

    @FXML
    public void initialize() {
        forgotPassword.setOnAction(this::handleHyperlinkClick);
        createAccount.setOnAction(this::handleCreateAccount);
        loginButton.setOnAction(this::handleLoginButton);
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

    private void handleLoginButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
