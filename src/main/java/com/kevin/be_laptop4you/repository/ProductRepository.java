package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Product;
import com.kevin.be_laptop4you.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
    Optional<Product> getProductById(Long productId);

    Page<Product> findAllByProductStatusAndNameContainingIgnoreCase(
            ProductStatus productStatus,
            String keyword,
            Pageable pageable
    );
    Page<Product> findAllByProductStatus(
            ProductStatus productStatus,
            Pageable pageable
    );


    // get san pham theo brand
   Page<Product> findByBrandId(Long brandId, Pageable  pageable);

   // tim san pham theo ten khong phan biet hoa thuong
    Page<Product> findByNameContainingIgnoreCase(String keyword,  Pageable  pageable);

    // tim san pham theo khoang gia
    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max,  Pageable  pageable);


}
