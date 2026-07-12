package com.kevin.be_laptop4you.controller;

import com.kevin.be_laptop4you.dto.request.BrandRequest;
import com.kevin.be_laptop4you.dto.response.BrandResponse;
import com.kevin.be_laptop4you.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(
            @Valid @RequestBody BrandRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(brandService.createBrand(request));
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandResponse> updateBrand(
            @PathVariable Long brandId,
            @Valid @RequestBody BrandRequest request
    ) {
        return ResponseEntity.ok(
                brandService.updateBrand(brandId, request)
        );
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponse> getBrandById(
            @PathVariable Long brandId
    ) {
        return ResponseEntity.ok(
                brandService.getBrandById(brandId)
        );
    }

    @GetMapping
    public ResponseEntity<Page<BrandResponse>> getAllBrands(
            @RequestParam(required = false) String keyword,
            @PageableDefault(
                    size = 10,
                    sort = "nameBrand",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                brandService.getAllBrands(keyword, pageable)
        );
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> deleteBrand(
            @PathVariable Long brandId
    ) {
        brandService.deleteBrand(brandId);

        return ResponseEntity.noContent().build();
    }
}
