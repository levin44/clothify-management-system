package org.example.controller.account;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.example.UserContext;
import org.example.entity.User;
import org.example.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class MyAccount {


    public JFXTextField oldPassField;
    public JFXTextField confirmNewPassField;
    public JFXTextField newPassField;
    private UserService userService = new UserService();

    User user;
    public void initialize() {
        User currentUser = UserContext.getInstance().getCurrentUser();
        user=currentUser;
    }

    public void handleUpdatePassword(ActionEvent actionEvent) {
        String oldpass = oldPassField.getText();
        String newpass = newPassField.getText();
        String confirmnewpass = confirmNewPassField.getText();
        if (!BCrypt.checkpw(oldpass, user.getPassword())) {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "Invalid password");
            return;
        }
        if(newpass.equals(confirmnewpass)){
            String hashedPassword = BCrypt.hashpw(newpass, BCrypt.gensalt());

            User userupdate = new User();

            userupdate.setUserId(user.getUserId());
            userupdate.setName(user.getName());
            userupdate.setPassword(hashedPassword);
            userupdate.setEmail(user.getEmail());
            userupdate.setRole(user.getRole());

            userService.updateUser(userupdate);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User created successfully!");

            // Clear the fields after successful update
            oldPassField.clear();
            newPassField.clear();
            confirmNewPassField.clear();
        }else {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match", "Reenter password");
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
