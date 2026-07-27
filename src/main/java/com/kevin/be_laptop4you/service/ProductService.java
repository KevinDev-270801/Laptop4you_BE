package com.kevin.be_laptop4you.service;

import com.kevin.be_laptop4you.dto.request.ProductRequest;
import com.kevin.be_laptop4you.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long productId, ProductRequest request);

    ProductResponse getProductById(Long productId);

    Page<ProductResponse> getAllProducts(String keyword,Pageable pageable); // for admin

    Page<ProductResponse> getActiveProducts(String keyword, Pageable pageable); // for clients

    ProductResponse activateProduct(Long productId);

    ProductResponse deactivateProduct(Long productId);

    void deleteProduct(Long productId);
}
