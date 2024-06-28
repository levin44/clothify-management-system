package org.example.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.UserContext;
import org.example.entity.User;

import java.net.URL;

public class AdminDashboard {

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
    @FXML
    public JFXButton userManagerButton;
    public Label welcomeLabel;
    @FXML
    private Pane mainContent;


    private User currentUser;
    public void setUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + currentUser.getName() + "!");
    }

    public void initialize() throws IOException {
        String fxmlFile = "/view/Order.fxml";
        URL fxmlUrl = getClass().getResource(fxmlFile);
        Node newContent = FXMLLoader.load(fxmlUrl);
        mainContent.getChildren().setAll(newContent);
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
                fxmlFile = "/view/AdminReports.fxml"; // Update this to the correct FXML file name
                break;
            case "userManagerButton":
                fxmlFile = "/view/AdminUserManger.fxml"; // Update this to the correct FXML file name
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
