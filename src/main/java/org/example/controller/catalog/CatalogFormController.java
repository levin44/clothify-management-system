package org.example.controller.catalog;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.entity.Product;
import org.example.entity.Supplier;
import org.example.service.ProductService;
import org.example.service.SupplierService;

import java.util.List;

public class CatalogFormController {
    public TableView tblProduct;
    public TableColumn colProductId;
    public TableColumn colName;
    public TableColumn colSize;
    public TableColumn colQuantity;
    public TableColumn colPrice;
    public TableColumn colCategory;
    public TableColumn colSupplier;
    public JFXTextField productIdField;
    public JFXTextField nameField;
    public JFXTextField sizeField;
    public JFXTextField quantityField;
    public JFXTextField catagoryField;
    public JFXTextField priceField;
    public JFXTextField supplierField;

    private ProductService productService = new ProductService();
    private SupplierService supplierService = new SupplierService();
    @FXML
    public void initialize() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        loadProductTable();
    }

    private void loadProductTable() {
        List<Product> products = productService.getAllProducts();
        tblProduct.getItems().setAll(products);
    }
    public void handleAddProduct(ActionEvent actionEvent) {
        try {
            String name = nameField.getText();
            String size = sizeField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            String category = catagoryField.getText();
            double price = Double.parseDouble(priceField.getText());
            int supplierId = Integer.parseInt(supplierField.getText());

            Supplier supplier = supplierService.getSupplierById(supplierId);
            if (supplier == null) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Invalid Supplier ID");
                return;
            }

            Product product = new Product();
            product.setName(name);
            product.setSize(size);
            product.setQuantity(quantity);
            product.setCategory(category);
            product.setPrice(price);
            product.setSupplier(supplier);

            productService.addProduct(product);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");

            // Clear the fields after successful addition
            nameField.clear();
            sizeField.clear();
            quantityField.clear();
            catagoryField.clear();
            priceField.clear();
            supplierField.clear();

            // Reload the table to reflect new data
            loadProductTable();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter valid numbers for Quantity, Price, and Supplier ID");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add product: " + e.getMessage());
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
    public void handleReturnProduct(ActionEvent actionEvent) {
    }

    public void handleUpdateProduct(ActionEvent actionEvent) {
    }

    public void handleGents(ActionEvent actionEvent) {
    }

    public void handleLadies(ActionEvent actionEvent) {
    }

    public void handleKids(ActionEvent actionEvent) {
    }

    public void handleRemoveProduct(ActionEvent actionEvent) {
    }

    public void handleAll(ActionEvent actionEvent) {
    }
}
