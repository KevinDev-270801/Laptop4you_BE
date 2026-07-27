package com.kevin.be_laptop4you.service.impl;

import com.kevin.be_laptop4you.dto.request.ProductRequest;
import com.kevin.be_laptop4you.dto.response.ProductResponse;
import com.kevin.be_laptop4you.entity.Brand;
import com.kevin.be_laptop4you.entity.Product;
import com.kevin.be_laptop4you.entity.Promotion;
import com.kevin.be_laptop4you.enums.ProductStatus;
import com.kevin.be_laptop4you.exception.AppException;
import com.kevin.be_laptop4you.exception.ErrorCode;
import com.kevin.be_laptop4you.mapper.ProductMapper;
import com.kevin.be_laptop4you.repository.BrandRepository;
import com.kevin.be_laptop4you.repository.ProductRepository;
import com.kevin.be_laptop4you.repository.PromotionRepository;
import com.kevin.be_laptop4you.service.ProductService;
import com.kevin.be_laptop4you.utils.SyncProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceIMPL implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Brand brand = brandRepository.getBrandById(request.getBrandId()).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found brand with id: " + request.getBrandId())
        );
        Promotion promotion = null;

        if (request.getPromotionId() != null) {
            promotion = promotionRepository.findById(request.getPromotionId())
                    .orElseThrow(() -> new AppException(
                            ErrorCode.RESOURCE_NOT_FOUND,
                            "Not found promotion with id: " + request.getPromotionId()
                    ));
        }

        Product product = productMapper.toEntity(request);
        product.setBrand(brand);
        product.setPromotion(promotion);
        product.setProductStatus(ProductStatus.DISABLED);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.getProductById(productId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found product with id: " + productId)
        );
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        "Not found brand with id: " + request.getBrandId()
                ));

        Promotion promotion = null;

        if (request.getPromotionId() != null) {
            promotion = promotionRepository.findById(request.getPromotionId())
                    .orElseThrow(() -> new AppException(
                            ErrorCode.RESOURCE_NOT_FOUND,
                            "Not found promotion with id: " + request.getPromotionId()
                    ));
        }
        productMapper.updateEntity(request,product);
        product.setBrand(brand);
        product.setPromotion(promotion);
        SyncProductStatus.syncProductStatusWithQuantity(product);
        Product saveProduct = productRepository.save(product);
        return productMapper.toResponse(saveProduct);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.getProductById(productId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found product with id: " + productId)
        );
        return productMapper.toResponse(product);
    }

    // get all products for admin include product active and non-active
    @Override
    public Page<ProductResponse> getAllProducts(String keyword, Pageable pageable) {
        Page<Product> productsPage;
        if(keyword == null || keyword.isEmpty()){
            productsPage = productRepository.findAll(pageable);
        }else {
            productsPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return productsPage.map(productMapper::toResponse);
    }

    // for client only product active
    @Override
    public Page<ProductResponse> getActiveProducts(String keyword, Pageable pageable) {
        Page<Product> productsPage;
        if(keyword == null || keyword.isEmpty()){
            productsPage = productRepository.findAllByProductStatus(ProductStatus.ACTIVE, pageable);
        }else{
            productsPage = productRepository.findAllByProductStatusAndNameContainingIgnoreCase(ProductStatus.ACTIVE,keyword, pageable);
        }
        return productsPage.map(productMapper::toResponse);
    }

    @Override
    @Transactional
    public ProductResponse activateProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        "Not found product with id: " + productId
                ));

        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        } else {
            product.setProductStatus(ProductStatus.ACTIVE);
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse deactivateProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        "Not found product with id: " + productId
                ));

        product.setProductStatus(ProductStatus.DISABLED);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        deactivateProduct(productId);
    }
}
