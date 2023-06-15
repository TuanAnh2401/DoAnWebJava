package com.example.DoAnWebJava.controller;

import com.example.DoAnWebJava.dto.ProductDto;
import com.example.DoAnWebJava.entities.Product;
import com.example.DoAnWebJava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> allProducts = productService.getAllProductDtos();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<ProductDto>> getActiveProducts() {
        List<ProductDto> activeProducts = productService.getActiveProductDtos();
        return ResponseEntity.ok(activeProducts);
    }

    @GetMapping("/getInactive")
    public ResponseEntity<List<ProductDto>> getInactiveProducts() {
        List<ProductDto> inactiveProducts = productService.getInactiveProductDtos();
        return ResponseEntity.ok(inactiveProducts);
    }

    @GetMapping("/getHome/{isHome}")
    public ResponseEntity<List<ProductDto>> getProductsByIsHome(@PathVariable boolean isHome) {
        List<ProductDto> products = productService.getProductDtosByIsHome(isHome);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getSale/{isSale}")
    public ResponseEntity<List<ProductDto>> getProductsByIsSale(@PathVariable boolean isSale) {
        List<ProductDto> products = productService.getProductDtosByIsSale(isSale);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getHot/{isHot}")
    public ResponseEntity<List<ProductDto>> getProductsByIsHot(@PathVariable boolean isHot) {
        List<ProductDto> products = productService.getProductDtosByIsHot(isHot);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getStatus/{isStatus}")
    public ResponseEntity<List<ProductDto>> getProductsByIsStatus(@PathVariable boolean isStatus) {
        List<ProductDto> products = productService.getProductDtosByIsStatus(isStatus);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {
        ProductDto product = productService.getProductDtoById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
        if (productDto != null) {
            ProductDto addedProduct = productService.addProduct(productDto);
            return ResponseEntity.ok("Product added successfully with ID: " + addedProduct.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        if (productDto != null) {
            ProductDto updatedProduct = productService.updateProduct(id, productDto);
            if (updatedProduct != null) {
                return ResponseEntity.ok("Product updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

