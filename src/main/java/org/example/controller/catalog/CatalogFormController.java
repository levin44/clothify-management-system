package org.example.controller.catalog;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.entity.Product;
import org.example.entity.Supplier;
import org.example.service.ProductService;
import org.example.service.SupplierService;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogFormController {
    public TableView<Product> tblProduct;
    public TableColumn<Product, Integer> colProductId;
    public TableColumn<Product, String> colName;
    public TableColumn<Product, String> colSize;
    public TableColumn<Product, Integer> colQuantity;
    public TableColumn<Product, Double> colPrice;
    public TableColumn<Product, String> colCategory;
    public TableColumn<Product, String> colSupplier;
    public JFXTextField productIdField;
    public JFXTextField nameField;
    public JFXTextField sizeField;
    public JFXTextField quantityField;
    @FXML
    private ComboBox<String> categoryCombo;
    public JFXTextField priceField;
    public JFXTextField supplierField;

    private ProductService productService = new ProductService();
    private SupplierService supplierService = new SupplierService();
    private List<Product> allProducts;

    @FXML
    public void initialize() {
        // Populate the combo box with categories
        categoryCombo.setItems(FXCollections.observableArrayList("Kids", "Gents", "Ladies"));

        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierName"));

        loadAllProducts();
        loadProductTable(allProducts);
    }

    private void loadAllProducts() {
        allProducts = productService.getAllProducts();
    }

    private void loadProductTable(List<Product> products) {
        tblProduct.getItems().setAll(products);
    }

    public void handleAddProduct(ActionEvent actionEvent) {
        try {
            String name = nameField.getText();
            String size = sizeField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryCombo.getValue();
            if (category == null) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please select a category");
                return;
            }
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

            // Clear the fields
            nameField.clear();
            sizeField.clear();
            quantityField.clear();
            categoryCombo.setValue(null);
            priceField.clear();
            supplierField.clear();

            // Reload the table
            loadAllProducts();
            loadProductTable(allProducts);

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
        try {
            int id = Integer.parseInt(productIdField.getText());
            String name = nameField.getText();
            String size = sizeField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryCombo.getValue();
            if (category == null) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please select a category");
                return;
            }
            double price = Double.parseDouble(priceField.getText());
            int supplierId = Integer.parseInt(supplierField.getText());

            Supplier supplier = supplierService.getSupplierById(supplierId);
            if (supplier == null) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Invalid Supplier ID");
                return;
            }

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setSize(size);
            product.setQuantity(quantity);
            product.setCategory(category);
            product.setPrice(price);
            product.setSupplier(supplier);

            productService.updateProduct(product);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product updated successfully!");

            // Clear the fields after successful update
            productIdField.clear();
            nameField.clear();
            sizeField.clear();
            quantityField.clear();
            categoryCombo.setValue(null);
            priceField.clear();
            supplierField.clear();

            // Reload the table
            loadAllProducts();
            loadProductTable(allProducts);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update product: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRemoveProduct(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(productIdField.getText());

            boolean isDeleted = productService.deleteProductById(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product removed successfully!");
                productIdField.clear();
                loadAllProducts();
                loadProductTable(allProducts);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Product not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove product: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadProductDetails(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(productIdField.getText());
            Product product = productService.getProductById(id);

            if (product != null) {
                nameField.setText(product.getName());
                sizeField.setText(product.getSize());
                categoryCombo.setValue(product.getCategory());
                supplierField.setText(String.valueOf(product.getSupplier().getId()));
                quantityField.setText(String.valueOf(product.getQuantity()));
                priceField.setText(String.valueOf(product.getPrice()));
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Product not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load product details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRegister(ActionEvent actionEvent) {
    }

    public void filterAll(ActionEvent actionEvent) {
        loadProductTable(allProducts);
    }

    public void filterKids(ActionEvent actionEvent) {
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> "Kids".equalsIgnoreCase(product.getCategory()))
                .collect(Collectors.toList());
        loadProductTable(filteredProducts);
    }

    public void filterGents(ActionEvent actionEvent) {
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> "Gents".equalsIgnoreCase(product.getCategory()))
                .collect(Collectors.toList());
        loadProductTable(filteredProducts);
    }

    public void filterLadies(ActionEvent actionEvent) {
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> "Ladies".equalsIgnoreCase(product.getCategory()))
                .collect(Collectors.toList());
        loadProductTable(filteredProducts);
    }
}
