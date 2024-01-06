package com.luna.test.service.serviceImpl;

import com.luna.test.dto.UpdateProductDto;
import com.luna.test.model.Product;
import com.luna.test.model.Tax;
import com.luna.test.repository.ProductRepository;
import com.luna.test.repository.TaxRepository;
import com.luna.test.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product request) {
        try {

            Product entity = new Product();
            entity.setSku(generateSku(request));
            entity.setItemName(request.getItemName());
            entity.setStock(request.getStock() != null? request.getStock() : 0.0);
            entity.setUom(request.getUom());
            entity.setCategory(request.getCategory());
            entity.setItemCost(request.getItemCost());
            entity.setItemPrice(request.getItemPrice());
            entity.setTaxId(request.getTaxId());

            return productRepository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    @Override
    public Product updateProduct(String productId, UpdateProductDto updateProductDto) {
        try {
            Product existingProduct = productRepository.findById(productId).get();

            // Update only the modifiable fields
            existingProduct.setItemName(updateProductDto.getItemName());
            existingProduct.setUom(updateProductDto.getUom());
            existingProduct.setCategory(updateProductDto.getCategory());
            existingProduct.setItemCost(updateProductDto.getItemCost());
            existingProduct.setItemPrice(updateProductDto.getItemPrice());
            existingProduct.setTaxId(updateProductDto.getTaxId());

            return productRepository.save(existingProduct);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Product not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

    @Override
    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private String generateSku(Product request) {
        if (request.getSku() != null) {
            return request.getSku();
        } else {
            String name = request.getItemName();
            String category = request.getCategory();

            return name.replaceAll("\\s+", "") + "_" + category.replaceAll("\\s+", "");
        }
    }

}
