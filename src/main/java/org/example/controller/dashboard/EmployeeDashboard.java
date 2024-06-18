package org.example.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.entity.User;

import java.io.IOException;
import java.net.URL;

public class EmployeeDashboard {

    @FXML
    public JFXButton orderButton;
    @FXML
    public JFXButton orderHistoryButton;
    @FXML
    public JFXButton catalogButton;
    @FXML
    public JFXButton supplierButton;
    @FXML
    public JFXButton reportsButton;
    @FXML
    public JFXButton myAccountButton;
    @FXML
    public JFXButton logoutButton;
    public Label welcomeLabel;
    @FXML
    private Pane mainContent;

    private User currentUser;
    public void setUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + currentUser.getName() + "!");
    }
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        JFXButton source = (JFXButton) event.getSource();
        String fxmlFile = "";

        switch (source.getId()) {
            case "orderButton":
                fxmlFile = "/view/Order.fxml";
                break;
            case "orderHistoryButton":
                fxmlFile = "/view/OrderHistory.fxml"; // Update this to the correct FXML file name
                break;
            case "catalogButton":
                fxmlFile = "/view/Catalog.fxml"; // Update this to the correct FXML file name
                break;
            case "supplierButton":
                fxmlFile = "/view/Supplier.fxml"; // Update this to the correct FXML file name
                break;
            case "reportsButton":
                fxmlFile = "/view/Reports.fxml"; // Update this to the correct FXML file name
                break;
            case "myAccountButton":
                fxmlFile = "/view/MyAccount.fxml"; // Update this to the correct FXML file name
                break;
            case "logoutButton":
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auth/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                break;
        }

        try {
            URL fxmlUrl = getClass().getResource(fxmlFile);
            if (fxmlUrl == null) {
                System.err.println("Cannot find FXML file: " + fxmlFile);
                return;
            }
            Node newContent = FXMLLoader.load(fxmlUrl);
            mainContent.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
