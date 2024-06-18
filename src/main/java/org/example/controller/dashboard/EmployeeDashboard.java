package org.example.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

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
    @FXML
    private Pane mainContent;

    @FXML
    public void handleButtonAction(ActionEvent event) {
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
                // Handle logout
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
