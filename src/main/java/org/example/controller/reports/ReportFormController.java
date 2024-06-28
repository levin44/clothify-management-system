package org.example.controller.reports;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.example.entity.Orders;
import org.example.entity.Supplier;
import org.example.entity.User;
import org.example.service.*;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.example.entity.Product;
import org.example.service.ProductService;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ReportFormController {
    @FXML
    private ComboBox<String> reportTypeComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    private ProductService productService = new ProductService();
    private UserService userService = new UserService();
    private SupplierService supplierService = new SupplierService();
    private SalesReportService salesReportService = new SalesReportService();
    public void printSupplierReport(ActionEvent actionEvent) {
        try {
            // Load the JRXML file from the resources directory
            InputStream reportStream = getClass().getResourceAsStream("/reports/supplier_report.jrxml");
            if (reportStream == null) {
                throw new IllegalArgumentException("Report file not found.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fetch the data from the database
            List<Supplier> suppliers = supplierService.getAllSuppliers();

            // Create a JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(suppliers);

            // Parameters for the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Inventory Report");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // View the report
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void printEmployeeReport(ActionEvent actionEvent) {
        try {
            // Load the JRXML file from the resources directory
            InputStream reportStream = getClass().getResourceAsStream("/reports/employee_report.jrxml");
            if (reportStream == null) {
                throw new IllegalArgumentException("Report file not found.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fetch the data from the database
            List<User> users = userService.getAllUsers();

            // Create a JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);

            // Parameters for the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Inventory Report");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // View the report
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void printInventoryReport(ActionEvent actionEvent) {
        try {
            // Load the JRXML file from the resources directory
            InputStream reportStream = getClass().getResourceAsStream("/reports/inventory_report.jrxml");
            if (reportStream == null) {
                throw new IllegalArgumentException("Report file not found.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fetch the data from the database
            List<Product> products = productService.getAllProducts();

            // Create a JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);

            // Parameters for the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Inventory Report");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // View the report
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void generateSalesReport(ActionEvent event) {
        String reportType = reportTypeComboBox.getValue();
        Date startDate = Date.valueOf(startDatePicker.getValue());
        Date endDate = Date.valueOf(endDatePicker.getValue());

        try {
            // Load the JRXML file from the resources directory
            InputStream reportStream = getClass().getResourceAsStream("/reports/sales_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fetch the data
            List<Orders> orders = fetchSalesData(reportType, startDate, endDate);

            // Convert the list to a JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orders);

            // Set parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Sales Report");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // View the report
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        reportTypeComboBox.setItems(FXCollections.observableArrayList("daily", "monthly", "annually"));
    }

    private List<Orders> fetchSalesData(String reportType, Date startDate, Date endDate) {
        switch (reportType.toLowerCase()) {
            case "daily":
                return salesReportService.getDailySales(startDate);
            case "monthly":
                return salesReportService.getMonthlySales(startDate, endDate);
            case "annually":
                return salesReportService.getAnnualSales(startDate, endDate);
            default:
                throw new IllegalArgumentException("Invalid report type.");
        }
    }
}
