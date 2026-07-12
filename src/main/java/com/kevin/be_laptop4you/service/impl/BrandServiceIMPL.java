package com.kevin.be_laptop4you.service.impl;

import com.kevin.be_laptop4you.dto.request.BrandRequest;
import com.kevin.be_laptop4you.dto.response.BrandResponse;
import com.kevin.be_laptop4you.entity.Brand;
import com.kevin.be_laptop4you.exception.AppException;
import com.kevin.be_laptop4you.exception.ErrorCode;
import com.kevin.be_laptop4you.mapper.BrandMapper;
import com.kevin.be_laptop4you.repository.BrandRepository;
import com.kevin.be_laptop4you.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceIMPL implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandResponse createBrand(BrandRequest request) {
        //check brand exist
        if (brandRepository.existsBrandByNameBrandEquals(request.getNameBrand())) {
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Tên thương hiệu đã tồn tại");
        }
        Brand brand = brandMapper.toEntity(request);
        brand.setStatus(true);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toResponse(savedBrand);
    }

    @Override
    public BrandResponse updateBrand(Long brandId, BrandRequest request) {
        Brand brandOld = brandRepository.getBrandById(brandId).orElseThrow(
                () -> new AppException(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        "Không tìm thấy thương hiệu có ID: " + brandId)
        );
        boolean duplicated = brandRepository.existsBrandByNameBrandEquals(request.getNameBrand());
        if (duplicated) {
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Tên thương hiệu đã tồn tại");
        }

        brandOld.setNameBrand(request.getNameBrand());
        Brand brandUpdated = brandRepository.save(brandOld);

        return brandMapper.toResponse(brandUpdated);
    }

    @Override
    public BrandResponse getBrandById(Long brandId) {
        Brand brand = brandRepository.getBrandById(brandId).orElseThrow(
                () -> new AppException(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        "Không tìm thấy thương hiệu có ID: " + brandId)
        );
        return brandMapper.toResponse(brand);
    }

    @Override
    public Page<BrandResponse> getAllBrands(String keyword, Pageable pageable) {

        Page<Brand> brands;
        if (keyword == null || keyword.isBlank()) {
            brands = brandRepository.findAll(pageable);
        } else {
            brands = brandRepository.findByNameBrandContainingIgnoreCase(keyword.trim(), pageable);
        }

        return brands.map(brandMapper::toResponse);
    }

    @Override
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.getBrandById(brandId).orElseThrow(
                () -> new AppException(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        "Không tìm thấy thương hiệu có ID: " + brandId)
        );
        brand.setStatus(!brand.isStatus());
        brandRepository.save(brand);
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
