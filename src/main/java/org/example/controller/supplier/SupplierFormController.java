package org.example.controller.supplier;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.entity.Supplier;
import org.example.service.CustomerService;
import org.example.service.SupplierService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SupplierFormController {
    public JFXTextField addNameField;
    public JFXTextField addCompanyField;
    public JFXTextField addEmailField;
    public JFXTextField updateId;
    public JFXTextField updateName;
    public JFXTextField updateCompany;
    public JFXTextField updateEmail;
    public JFXTextField removeSupplierId;
    public TableView tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colEmail;

    private SupplierService supplierService = new SupplierService();

    @FXML
    public void initialize() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadSupplierTable();
    }
    private void loadSupplierTable() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList(suppliers);
        tblSupplier.setItems(supplierList);
    }

    public void handleAddSupplier(ActionEvent actionEvent) {
        try {
            String name = addNameField.getText();
            String company = addCompanyField.getText();
            String email = addEmailField.getText();

            if (name.isEmpty() || company.isEmpty()|| email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter supplier ID and name");
                return;
            }
            Supplier supplier = new Supplier();
            supplier.setName(name);
            supplier.setEmail(email);
            supplier.setCompany(company);

            supplierService.addSupplier(supplier);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier added successfully!");

            // Clear the fields after successful addition
            addNameField.clear();
            addCompanyField.clear();
            addEmailField.clear();
            loadSupplierTable();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add supplier: " + e.getMessage());
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

    public void handleUpdateButton(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(updateId.getText());
            String name = updateName.getText();
            String company = updateCompany.getText();
            String email = updateEmail.getText();

            if (name.isEmpty() || company.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields");
                return;
            }

            Supplier supplier = new Supplier();
            supplier.setId(id);
            supplier.setName(name);
            supplier.setCompany(company);
            supplier.setEmail(email);

            supplierService.updateSupplier(supplier);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier updated successfully!");

            // Clear the fields after successful update
            updateId.clear();
            updateName.clear();
            updateCompany.clear();
            updateEmail.clear();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update supplier: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadSupplierDetails(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(updateId.getText());
            Supplier supplier = supplierService.getSupplierById(id);

            if (supplier != null) {
                updateName.setText(supplier.getName());
                updateCompany.setText(supplier.getCompany());
                updateEmail.setText(supplier.getEmail());
                //enable text fields
                updateName.setDisable(false);
                updateEmail.setDisable(false);
                updateCompany.setDisable(false);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Supplier not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load supplier details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRemoveSupplier(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(removeSupplierId.getText());

            boolean isDeleted = supplierService.deleteSupplierById(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier removed successfully!");
                removeSupplierId.clear();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Supplier not found");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove supplier: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
