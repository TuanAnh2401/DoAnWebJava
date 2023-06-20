package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.ProductDto;
import com.example.DoAnWebJava.entities.Product;
import com.example.DoAnWebJava.entities.ProductCategory;
import com.example.DoAnWebJava.entities.Supplier;
import com.example.DoAnWebJava.repositories.ProductCategoryRepository;
import com.example.DoAnWebJava.repositories.ProductRepository;
import com.example.DoAnWebJava.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProductDtos() {
        List<Product> products = productRepository.findAll();
        return convertToDtoList(products);
    }



    public List<ProductDto> getProductDtosByIsActive(boolean isActive) {
        List<Product> products = productRepository.findByIsActivate(isActive);
        return convertToDtoList(products);
    }

    public List<ProductDto> getProductDtosByIsHome(boolean isHome) {
        List<Product> products = productRepository.findByIsHome(isHome);
        return convertToDtoList(products);
    }

    public List<ProductDto> getProductDtosByIsSale(boolean isSale) {
        List<Product> products = productRepository.findByIsSale(isSale);
        return convertToDtoList(products);
    }

    public List<ProductDto> getProductDtosByIsHot(boolean isHot) {
        List<Product> products = productRepository.findByIsHot(isHot);
        return convertToDtoList(products);
    }

    public List<ProductDto> getProductDtosByIsStatus(boolean isStatus) {
        List<Product> products = productRepository.findByIsStatus(isStatus);
        return convertToDtoList(products);
    }

    public ProductDto getProductDtoById(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(this::convertToDto).orElse(null);
    }

    public ProductDto addProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        product.setCreatedDate(new Date());
        product.setModifiedDate(new Date());
        Product addedProduct = productRepository.save(product);
        return convertToDto(addedProduct);
    }

    public ProductDto updateProduct(int id, ProductDto productDto) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            Product updatedProduct = convertToEntity(productDto);
            existingProduct.setTitle(updatedProduct.getTitle());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setDetail(updatedProduct.getDetail());
            existingProduct.setImage(updatedProduct.getImage());
            existingProduct.setOriginalPrice(updatedProduct.getOriginalPrice());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setPriceSale(updatedProduct.getPriceSale());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setHome(updatedProduct.isHome());
            existingProduct.setSale(updatedProduct.isSale());
            existingProduct.setHot(updatedProduct.isHot());
            existingProduct.setActivate(updatedProduct.isActivate());
            existingProduct.setStatus(updatedProduct.isStatus());
            existingProduct.setModifiedDate(new Date());
            existingProduct.setProductCategory(updatedProduct.getProductCategory());
            existingProduct.setSupplier(updatedProduct.getSupplier());
            Product savedProduct = productRepository.save(existingProduct);
            return convertToDto(savedProduct);
        }
        return null;
    }

    public boolean deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private List<ProductDto> convertToDtoList(List<Product> products) {
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setDetail(product.getDetail());
        productDto.setImage(product.getImage());
        productDto.setOriginalPrice(product.getOriginalPrice());
        productDto.setPrice(product.getPrice());
        productDto.setPriceSale(product.getPriceSale());
        productDto.setQuantity(product.getQuantity());
        productDto.setHome(product.isHome());
        productDto.setSale(product.isSale());
        productDto.setHot(product.isHot());
        productDto.setActivate(product.isActivate());
        productDto.setStatus(product.isStatus());
        productDto.setProductCategoryId(product.getProductCategory().getId());
        productDto.setSupplierId(product.getSupplier().getId());
        return productDto;
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setDetail(productDto.getDetail());
        product.setImage(productDto.getImage());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setPrice(productDto.getPrice());
        product.setPriceSale(productDto.getPriceSale());
        product.setQuantity(productDto.getQuantity());
        product.setHome(productDto.isHome());
        product.setSale(productDto.isSale());
        product.setHot(productDto.isHot());
        product.setActivate(productDto.isActivate());
        product.setStatus(productDto.isStatus());
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(productDto.getProductCategoryId());
        Optional<Supplier> supplierOptional = supplierRepository.findById(productDto.getSupplierId());
        if (productCategoryOptional.isPresent() && supplierOptional.isPresent()) {
            product.setProductCategory(productCategoryOptional.get());
            product.setSupplier(supplierOptional.get());
        }
        return product;
    }
}
