package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Supplier;
import com.example.DoAnWebJava.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findByIsActivate(true);
    }

    public List<Supplier> getInactiveSuppliers() {
        return supplierRepository.findByIsActivate(false);
    }

    public Supplier getSupplierById(int id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public Supplier addSupplier(Supplier supplier) {
        supplier.setCreatedDate(new Date());
        supplier.setModifiedDate(new Date());
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(int id, Supplier updatedSupplier) {
        Supplier existingSupplier = supplierRepository.findById(id).orElse(null);
        if (existingSupplier != null) {
            existingSupplier.setTitle(updatedSupplier.getTitle());
            existingSupplier.setDescription(updatedSupplier.getDescription());
            existingSupplier.setImage(updatedSupplier.getImage());
            existingSupplier.setActivate(updatedSupplier.isActivate());
            existingSupplier.setModifiedDate(new Date());
            // Update other properties of Supplier if needed
            return supplierRepository.save(existingSupplier);
        }
        throw new IllegalArgumentException("Supplier not found");
    }

    public void deleteSupplier(int id) {
        Supplier existingSupplier = supplierRepository.findById(id).orElse(null);
        if (existingSupplier != null) {
            supplierRepository.delete(existingSupplier);
        } else {
            throw new IllegalArgumentException("Supplier not found");
        }
    }
}
