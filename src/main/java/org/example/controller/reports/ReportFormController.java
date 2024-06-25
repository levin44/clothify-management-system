package org.example.controller.reports;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import org.example.service.ProductService;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.example.entity.Product;
import org.example.service.ProductService;

import java.io.InputStream;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ReportFormController {
    private ProductService productService = new ProductService();
    public void printSupplierReport(ActionEvent actionEvent) {
    }

    public void printEmployeeReport(ActionEvent actionEvent) {
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
}
