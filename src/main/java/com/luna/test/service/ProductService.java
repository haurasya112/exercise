package com.luna.test.service;

import com.luna.test.dto.UpdateProductDto;
import com.luna.test.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product request);
    Product updateProduct(String productId, UpdateProductDto updateProductDto);
    void deleteProduct(String productId);
    Optional<Product> getProductById(String productId);
    List<Product> getAllProducts();
}
