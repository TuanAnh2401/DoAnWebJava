package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Adv;
import com.example.DoAnWebJava.entities.ProductCategory;
import com.example.DoAnWebJava.repositories.ProductCategoryRepository;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public List<ProductCategory> getActiveProductCategories() {
        return productCategoryRepository.findByIsActivate(true);
    }

    public List<ProductCategory> getInactiveProductCategories() {
        return productCategoryRepository.findByIsActivate(false);
    }


    public ProductCategory getProductCategoryById(int id) {
        return productCategoryRepository.findById(id).orElse(null);
    }

    public ProductCategory addProductCategory(ProductCategory productCategory) {
        productCategory.setCreatedDate(new Date());
        productCategory.setModifiedDate(new Date());
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory updateProductCategory(int id, ProductCategory updatedProductCategory) throws UserRegistrationException{
        ProductCategory existingProductCategory = productCategoryRepository.findById(id).orElse(null);
        if (existingProductCategory != null) {
            existingProductCategory.setTitle(updatedProductCategory.getTitle());
            existingProductCategory.setDescription(updatedProductCategory.getDescription());
            existingProductCategory.setImage(updatedProductCategory.getImage());
            existingProductCategory.setActivate(updatedProductCategory.isActivate());
            existingProductCategory.setModifiedDate(new Date());
            // Cập nhật các thuộc tính khác của ProductCategory
            return productCategoryRepository.save(existingProductCategory);
        }
        throw new UserRegistrationException("Product Category not found");
    }

    public void deleteProductCategory(int id) throws UserRegistrationException{
        ProductCategory existingProductCategory = productCategoryRepository.findById(id).orElse(null);
        if (existingProductCategory != null) {
            productCategoryRepository.delete(existingProductCategory);
        } else {
            throw new UserRegistrationException("Product Category not found");
        }
    }
}
