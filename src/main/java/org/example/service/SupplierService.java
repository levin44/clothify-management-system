package org.example.service;

import org.example.dao.CustomerDAO;
import org.example.dao.SupplierDAO;
import org.example.entity.Customer;
import org.example.entity.Supplier;

import java.util.List;

public class SupplierService {
    private SupplierDAO supplierDAO = new SupplierDAO();

    public void addSupplier(Supplier supplier) {
        supplierDAO.saveSupplier(supplier);
    }

    public Supplier getSupplierById(int id) {
        return supplierDAO.getSupplierById(id);
    }

    public void updateSupplier(Supplier supplier) {
        supplierDAO.updateSupplier(supplier);
    }
    public boolean deleteSupplierById(int id) {
        return supplierDAO.deleteSupplierById(id);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }
}

