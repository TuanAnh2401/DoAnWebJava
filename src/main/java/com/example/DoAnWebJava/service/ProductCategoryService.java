package com.example.DoAnWebJava.service;

import com.example.DoAnWebJava.dto.ProductCategoryDto;
import com.example.DoAnWebJava.entities.ProductCategory;
import com.example.DoAnWebJava.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategoryDto> getAllProductCategoryDtos() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        return convertToDtoList(productCategories);
    }

    public List<ProductCategoryDto> getProductCategoryDtosByActivate(boolean isActivate) {
        List<ProductCategory> activeProductCategories = productCategoryRepository.findByIsActivate(isActivate);
        return convertToDtoList(activeProductCategories);
    }

    public ProductCategoryDto getProductCategoryDtoById(int id) {
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
        return productCategoryOptional.map(this::convertToDto).orElse(null);
    }

    public ProductCategoryDto addProductCategory(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = convertToEntity(productCategoryDto);
        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);
        return convertToDto(savedProductCategory);
    }

    public ProductCategoryDto updateProductCategory(int id, ProductCategoryDto productCategoryDto) {
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);
        if (productCategoryOptional.isPresent()) {
            ProductCategory productCategory = productCategoryOptional.get();
            productCategory.setTitle(productCategoryDto.getTitle());
            productCategory.setDescription(productCategoryDto.getDescription());
            productCategory.setImage(productCategoryDto.getImage());
            productCategory.setActivate(productCategoryDto.isActivate());
            ProductCategory updatedProductCategory = productCategoryRepository.save(productCategory);
            return convertToDto(updatedProductCategory);
        }
        return null;
    }

    public boolean deleteProductCategory(int id) {
        if (productCategoryRepository.existsById(id)) {
            productCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private List<ProductCategoryDto> convertToDtoList(List<ProductCategory> productCategories) {
        return productCategories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductCategoryDto convertToDto(ProductCategory productCategory) {
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setId(productCategory.getId());
        productCategoryDto.setTitle(productCategory.getTitle());
        productCategoryDto.setDescription(productCategory.getDescription());
        productCategoryDto.setImage(productCategory.getImage());
        productCategoryDto.setActivate(productCategory.isActivate());
        productCategoryDto.setCreatedDate(productCategory.getCreatedDate());
        productCategoryDto.setModifiedDate(productCategory.getModifiedDate());
        return productCategoryDto;
    }

    private ProductCategory convertToEntity(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productCategoryDto.getId());
        productCategory.setTitle(productCategoryDto.getTitle());
        productCategory.setDescription(productCategoryDto.getDescription());
        productCategory.setImage(productCategoryDto.getImage());
        productCategory.setActivate(productCategoryDto.isActivate());
        productCategory.setCreatedDate(productCategoryDto.getCreatedDate());
        productCategory.setModifiedDate(productCategoryDto.getModifiedDate());
        return productCategory;
    }
}
