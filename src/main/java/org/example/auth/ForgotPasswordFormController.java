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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.example.util.EmailUtil.sendEmail;

public class ForgotPasswordFormController {
    public Hyperlink returnToLogin;
    public JFXTextField emailField;
    public JFXTextField codeField;
    public JFXTextField confirmPasswordField;
    public JFXTextField passwordField;
    private Map<String, Integer> otpStorage = new HashMap<>();

    private UserService userService = new UserService();
    int otp;
    String email;

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

    public void btnSendOtp(ActionEvent actionEvent) {
         email = emailField.getText();
        if (email == null || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Error", "Please enter a valid email address.");
            return;
        }

        otp = generateOtp();


        sendOtpEmail(email, otp);
        showAlert(Alert.AlertType.CONFIRMATION, "Success", "OTP has been sent to your email address.");
    }

    public void btnResetPassword(ActionEvent actionEvent) {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR,"Error", "Passwords do not match.");
            return;
        }
//reset password
        User user = userService.getUserByEmail(email);

        if (user != null) {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User usercreate = new User();

            usercreate.setUserId(user.getUserId());
            usercreate.setName(user.getName());
            usercreate.setPassword(hashedPassword);
            usercreate.setEmail(email);
            usercreate.setRole(user.getRole());

            userService.updateUser(usercreate);

            showAlert(Alert.AlertType.CONFIRMATION,"Success", "Your password has been reset successfully.");
            // Clear the fields after successful update
            emailField.clear();
            codeField.clear();
            passwordField.clear();
            confirmPasswordField.clear();

        } else {
            showAlert(Alert.AlertType.ERROR, "User Not Found", "Please contact Admin to create account");
        }

    }

    public static void sendOtpEmail(String to, int otp) {
        String subject = "Your OTP Code";
        String body = String.format("Dear User,%n%nYour OTP code is %04d.%n%nThank you,%nYour Company", otp);
        sendEmail(to, subject, body);
    }
    private int generateOtp() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnVarify(ActionEvent actionEvent) {
        int code = Integer.parseInt(codeField.getText());
        if(code!=otp){
            showAlert(Alert.AlertType.ERROR, "Error", "OTP doesn`t match.");
        }
        passwordField.setDisable(false);
        confirmPasswordField.setDisable(false);
        showAlert(Alert.AlertType.CONFIRMATION, "Success", "Please enter new password");
    }
}
