package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.ProductDto;
import com.example.DoAnWebJava.dto.SupplierDto;
import com.example.DoAnWebJava.entities.Product;
import com.example.DoAnWebJava.entities.Supplier;
import com.example.DoAnWebJava.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierDto> getAllSupplierDtos() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return convertToDtoList(suppliers);
    }
    public List<SupplierDto> getSupplierDtosByIsActive(boolean isActive) {
        List<Supplier> suppliers = supplierRepository.findByIsActivate(isActive);
        return convertToDtoList(suppliers);
    }

    public SupplierDto getSupplierDtoById(int id) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        return supplierOptional.map(this::convertToDto).orElse(null);
    }

    public SupplierDto addSupplier(SupplierDto supplierDto) {
        Supplier supplier = convertToEntity(supplierDto);
        supplier.setCreatedDate(new Date());
        supplier.setModifiedDate(new Date());
        Supplier addedSupplier = supplierRepository.save(supplier);
        return convertToDto(addedSupplier);
    }

    public SupplierDto updateSupplier(int id, SupplierDto supplierDto) {
        Optional<Supplier> existingSupplierOptional = supplierRepository.findById(id);
        if (existingSupplierOptional.isPresent()) {
            Supplier existingSupplier = existingSupplierOptional.get();
            Supplier updatedSupplier = convertToEntity(supplierDto);
            existingSupplier.setTitle(updatedSupplier.getTitle());
            existingSupplier.setDescription(updatedSupplier.getDescription());
            existingSupplier.setImage(updatedSupplier.getImage());
            existingSupplier.setActivate(updatedSupplier.isActivate());
            existingSupplier.setModifiedDate(new Date());
            Supplier savedSupplier = supplierRepository.save(existingSupplier);
            return convertToDto(savedSupplier);
        }
        return null;
    }

    public boolean deleteSupplier(int id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private List<SupplierDto> convertToDtoList(List<Supplier> suppliers) {
        return suppliers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SupplierDto convertToDto(Supplier supplier) {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(supplier.getId());
        supplierDto.setTitle(supplier.getTitle());
        supplierDto.setDescription(supplier.getDescription());
        supplierDto.setImage(supplier.getImage());
        supplierDto.setActivate(supplier.isActivate());
        supplierDto.setCreatedDate(supplier.getCreatedDate());
        supplierDto.setModifiedDate(supplier.getModifiedDate());
        return supplierDto;
    }

    private Supplier convertToEntity(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierDto.getId());
        supplier.setTitle(supplierDto.getTitle());
        supplier.setDescription(supplierDto.getDescription());
        supplier.setImage(supplierDto.getImage());
        supplier.setActivate(supplierDto.isActivate());
        supplier.setCreatedDate(supplierDto.getCreatedDate());
        supplier.setModifiedDate(supplierDto.getModifiedDate());
        return supplier;
    }
}
