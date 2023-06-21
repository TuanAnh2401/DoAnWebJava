package com.example.DoAnWebJava.controller.api;

import com.example.DoAnWebJava.dto.OrderDto;
import com.example.DoAnWebJava.dto.ProductDto;
import com.example.DoAnWebJava.entities.Product;
import com.example.DoAnWebJava.service.ProductService;
import com.example.DoAnWebJava.support.ResponsePaging;
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

    @GetMapping("/paginate")
    public ResponseEntity<ResponsePaging<List<ProductDto>>> getPaginatedContacts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String searchString,
            @RequestParam(defaultValue = "true") boolean isActivate
    ) {
        int pageSize = 10; // Kích thước trang (số lượng liên hệ trên mỗi trang)
        int totalContacts = productService.getTotalProducts(searchString, isActivate);
        int totalPages = (int) Math.ceil((double) totalContacts / pageSize);

        // Giới hạn số trang hiện tại trong khoảng từ 1 đến tổng số trang
        page = Math.max(1, Math.min(page, totalPages));

        // Lấy danh sách liên hệ phân trang từ Service
        List<ProductDto> contacts = productService.getPaginatedProducts(page, pageSize, searchString, isActivate);

        // Tạo đối tượng ResponsePaging để chứa thông tin phân trang và danh sách liên hệ
        ResponsePaging<List<ProductDto>> responsePaging = new ResponsePaging<>(contacts, totalPages, page, totalContacts);

        return ResponseEntity.ok(responsePaging);
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
            ProductDto addProduct = productService.addProduct(productDto);
            return ResponseEntity.ok("Product added successfully with ID: " + addProduct.getId());
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

