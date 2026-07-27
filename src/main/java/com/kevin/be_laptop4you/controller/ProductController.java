package com.kevin.be_laptop4you.controller;

import com.kevin.be_laptop4you.dto.request.ProductRequest;
import com.kevin.be_laptop4you.dto.response.ProductResponse;
import com.kevin.be_laptop4you.service.ProductService;
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
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct (@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequest));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct (@PathVariable Long productId,
                                                          @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(productId, productRequest));
    }

    @PatchMapping("/{productId}/activate")
    public ResponseEntity<ProductResponse> activateProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                productService.activateProduct(productId)
        );
    }

    @PatchMapping("/{productId}/deactivate")
    public ResponseEntity<ProductResponse> deactivateProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                productService.deactivateProduct(productId)
        );
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(
                    size = 10,
                    page = 0,
                    direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(productService.getActiveProducts(keyword, pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<ProductResponse>> getAllAdminProducts(@RequestParam(defaultValue = "") String keyword,
                                                                     @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC)  Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(keyword, pageable));
    }
}
