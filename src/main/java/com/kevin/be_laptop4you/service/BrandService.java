package com.kevin.be_laptop4you.service;

import com.kevin.be_laptop4you.dto.request.BrandRequest;
import com.kevin.be_laptop4you.dto.response.BrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {

    BrandResponse createBrand(BrandRequest request);
    BrandResponse updateBrand(Long brandId, BrandRequest request);
    BrandResponse getBrandById(Long brandId);
    Page<BrandResponse> getAllBrands(
            String keyword,
            Pageable pageable
    );
    void deleteBrand(Long brandId);
    boolean existsByName(String name);
}
