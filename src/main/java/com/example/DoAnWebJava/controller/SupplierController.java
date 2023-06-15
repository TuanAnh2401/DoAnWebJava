package com.example.DoAnWebJava.controller;

import com.example.DoAnWebJava.entities.Supplier;
import com.example.DoAnWebJava.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> allSuppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(allSuppliers);
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<Supplier>> getActiveSuppliers() {
        List<Supplier> activeSuppliers = supplierService.getActiveSuppliers();
        return ResponseEntity.ok(activeSuppliers);
    }

    @GetMapping("/getInactive")
    public ResponseEntity<List<Supplier>> getInactiveSuppliers() {
        List<Supplier> inactiveSuppliers = supplierService.getInactiveSuppliers();
        return ResponseEntity.ok(inactiveSuppliers);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable int id) {
        Supplier supplier = supplierService.getSupplierById(id);
        if (supplier != null) {
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSupplier(@RequestBody Supplier supplier) {
        if (supplier != null) {
            Supplier addedSupplier = supplierService.addSupplier(supplier);
            return ResponseEntity.ok("Supplier added successfully with ID: " + addedSupplier.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSupplier(@PathVariable int id, @RequestBody Supplier supplier) {
        if (supplier != null) {
            Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
            return ResponseEntity.ok("Supplier updated successfully");
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully");
    }
}
