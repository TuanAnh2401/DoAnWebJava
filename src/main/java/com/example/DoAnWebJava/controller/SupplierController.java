package com.example.DoAnWebJava.controller;

import com.example.DoAnWebJava.dto.SupplierDto;
import com.example.DoAnWebJava.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        List<SupplierDto> supplierDtos = supplierService.getAllSupplierDtos();
        return new ResponseEntity<>(supplierDtos, HttpStatus.OK);
    }
    @GetMapping("/getActive/{isActive}")
    public ResponseEntity<List<SupplierDto>> getActiveSuppliers(@PathVariable boolean isActive) {
        List<SupplierDto> activeSupplierDtos = supplierService.getSupplierDtosByIsActive(isActive);
        return new ResponseEntity<>(activeSupplierDtos, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable int id) {
        SupplierDto supplierDto = supplierService.getSupplierDtoById(id);
        if (supplierDto != null) {
            return new ResponseEntity<>(supplierDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<SupplierDto> addSupplier(@RequestBody SupplierDto supplierDto) {
        SupplierDto addedSupplier = supplierService.addSupplier(supplierDto);
        return new ResponseEntity<>(addedSupplier, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable int id, @RequestBody SupplierDto supplierDto) {
        SupplierDto updatedSupplier = supplierService.updateSupplier(id, supplierDto);
        if (updatedSupplier != null) {
            return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
        boolean deleted = supplierService.deleteSupplier(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
