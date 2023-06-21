package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.entities.ProductCategory;
import com.example.DoAnWebJava.repositories.UserRegistrationException;
import com.example.DoAnWebJava.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        List<ProductCategory> allProductCategories = productCategoryService.getAllProductCategories();
        return ResponseEntity.ok(allProductCategories);
    }



    @GetMapping("/getByActivate/{isActivate}")
    public ResponseEntity<List<ProductCategory>> getProductCategoriesByActivate(@PathVariable boolean isActivate) {
        List<ProductCategory> activeProductCategories = productCategoryService.getProductCategoriesByActivate(isActivate);
        return ResponseEntity.ok(activeProductCategories);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable int id) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        if (productCategory != null) {
            return ResponseEntity.ok(productCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategory != null) {
            ProductCategory addedProductCategory = productCategoryService.addProductCategory(productCategory);
            return ResponseEntity.ok("Product Category added successfully with ID: " + addedProductCategory.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateProductCategory(@PathVariable int id, @RequestBody ProductCategory productCategory) throws UserRegistrationException {
        if (productCategory != null) {
            try {
                ProductCategory updatedProductCategory = productCategoryService.updateProductCategory(id, productCategory);
                return ResponseEntity.ok("Product Category updated successfully");
            } catch (UserRegistrationException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable int id) {
        try {
            productCategoryService.deleteProductCategory(id);
            return ResponseEntity.ok("Product Category deleted successfully");
        } catch (UserRegistrationException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
