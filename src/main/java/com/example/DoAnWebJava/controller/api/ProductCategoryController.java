package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.ProductCategoryDto;
import com.example.DoAnWebJava.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProductCategoryDto>> getAllProductCategories() {
        List<ProductCategoryDto> productCategoryDtos = productCategoryService.getAllProductCategoryDtos();
        return new ResponseEntity<>(productCategoryDtos, HttpStatus.OK);
    }

    @GetMapping("/getByActivate/{isActivate}")
    public ResponseEntity<List<ProductCategoryDto>> getProductCategoriesByActivate(@PathVariable boolean isActivate) {
        List<ProductCategoryDto> activeProductCategoryDtos = productCategoryService.getProductCategoryDtosByActivate(isActivate);
        return new ResponseEntity<>(activeProductCategoryDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> getProductCategoryById(@PathVariable int id) {
        ProductCategoryDto productCategoryDto = productCategoryService.getProductCategoryDtoById(id);
        if (productCategoryDto != null) {
            return new ResponseEntity<>(productCategoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductCategoryDto> addProductCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        ProductCategoryDto addedProductCategory = productCategoryService.addProductCategory(productCategoryDto);
        return new ResponseEntity<>(addedProductCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductCategoryDto> updateProductCategory(@PathVariable int id, @RequestBody ProductCategoryDto productCategoryDto) {
        ProductCategoryDto updatedProductCategory = productCategoryService.updateProductCategory(id, productCategoryDto);
        if (updatedProductCategory != null) {
            return new ResponseEntity<>(updatedProductCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable int id) {
        boolean deleted = productCategoryService.deleteProductCategory(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
