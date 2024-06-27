package org.example.controller.reports;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.example.entity.Supplier;
import org.example.entity.User;
import org.example.service.ProductService;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.example.entity.Product;
import org.example.service.ProductService;
import org.example.service.SupplierService;
import org.example.service.UserService;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ReportFormController {
    private ComboBox<String> reportTypeComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    private ProductService productService = new ProductService();
    private UserService userService = new UserService();
    private SupplierService supplierService = new SupplierService();
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

    public void printSalesReport(ActionEvent actionEvent, String reportType, Date startDate, Date endDate) {
        try {
            // Load the JRXML file from the resources directory
            InputStream reportStream = getClass().getResourceAsStream("/reports/sales_report.jrxml");
            if (reportStream == null) {
                throw new IllegalArgumentException("Report file not found.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fetch the data from the database
//            List<Sales> sales;
//            switch (reportType) {
//                case "daily":
//                    sales = salesService.getDailySales(startDate);
//                    break;
//                case "monthly":
//                    sales = salesService.getMonthlySales(startDate, endDate);
//                    break;
//                case "annually":
//                    sales = salesService.getAnnualSales(startDate, endDate);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Invalid report type.");
//            }

//            // Create a JRBeanCollectionDataSource
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sales);
//
//            // Parameters for the report
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("ReportTitle", "Sales Report");
//
//            // Fill the report
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//            // View the report
//            JasperViewer viewer = new JasperViewer(jasperPrint, false);
//            viewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generateSalesReport(ActionEvent event) {
        String reportType = reportTypeComboBox.getValue();
        Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
        Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());

        printSalesReport(event, reportType, startDate, endDate);
    }
}
