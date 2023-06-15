package com.example.DoAnWebJava.controller;

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
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<Product>> getActiveProducts() {
        List<Product> activeProducts = productService.getActiveProducts();
        return ResponseEntity.ok(activeProducts);
    }

    @GetMapping("/getInactive")
    public ResponseEntity<List<Product>> getInactiveProducts() {
        List<Product> inactiveProducts = productService.getInactiveProducts();
        return ResponseEntity.ok(inactiveProducts);
    }
    @GetMapping("/getHome/{isHome}")
    public ResponseEntity<List<Product>> getProductsByIsHome(@PathVariable boolean isHome) {
        List<Product> products = productService.getProductsByIsHome(isHome);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getSale/{isSale}")
    public ResponseEntity<List<Product>> getProductsByIsSale(@PathVariable boolean isSale) {
        List<Product> products = productService.getProductsByIsSale(isSale);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getHot/{isHot}")
    public ResponseEntity<List<Product>> getProductsByIsHot(@PathVariable boolean isHot) {
        List<Product> products = productService.getProductsByIsHot(isHot);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getStatus/{isStatus}")
    public ResponseEntity<List<Product>> getProductsByIsStatus(@PathVariable boolean isStatus) {
        List<Product> products = productService.getProductsByIsStatus(isStatus);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        if (product != null) {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity.ok("Product added successfully with ID: " + addedProduct.getId());
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product product) {
        if (product != null) {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok("Product updated successfully");
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
