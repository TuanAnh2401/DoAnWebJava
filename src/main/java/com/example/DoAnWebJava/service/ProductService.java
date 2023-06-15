package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.entities.Product;
import com.example.DoAnWebJava.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> getActiveProducts() {
        return productRepository.findByIsActivate(true);
    }


    public List<Product> getInactiveProducts() {
        return productRepository.findByIsActivate(false);
    }

    public List<Product> getProductsByIsHome(boolean isHome) {
        return productRepository.findByIsHome(isHome);
    }


    public List<Product> getProductsByIsSale(boolean isSale) {
        return productRepository.findByIsSale(isSale);
    }

    public List<Product> getProductsByIsHot(boolean isHot) {
        return productRepository.findByIsHot(isHot);
    }
    public List<Product> getProductsByIsStatus(boolean isStatus) {
        return productRepository.findByIsStatus(isStatus);
    }
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    public Product addProduct(Product product) {
        product.setCreatedDate(new Date());
        product.setModifiedDate(new Date());
        return productRepository.save(product);
    }
    public Product updateProduct(int id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setTitle(product.getTitle());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setDetail(product.getDetail());
            existingProduct.setImage(product.getImage());
            existingProduct.setOriginalPrice(product.getOriginalPrice());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setPriceSale(product.getPriceSale());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setHome(product.isHome());
            existingProduct.setSale(product.isSale());
            existingProduct.setHot(product.isHot());

            existingProduct.setActivate(product.isActivate());
            existingProduct.setStatus(product.isStatus());
            existingProduct.setModifiedDate(new Date());
            existingProduct.setProductCategory(product.getProductCategory());
            existingProduct.setSupplier(product.getSupplier());
            existingProduct.setProductImage(product.getProductImage());
            existingProduct.setOrderDetails(product.getOrderDetails());
            return productRepository.save(existingProduct);
        }
        return null;
    }
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
