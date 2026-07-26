package com.kevin.be_laptop4you.controller;

import com.kevin.be_laptop4you.dto.request.SupplierRequest;
import com.kevin.be_laptop4you.dto.response.SupplierResponse;
import com.kevin.be_laptop4you.entity.Supplier;
import com.kevin.be_laptop4you.service.SupplierService;
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
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody @Valid SupplierRequest supplierRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supplierService.createSupplier(supplierRequest));
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable Long supplierId
            ,@RequestBody @Valid SupplierRequest supplierRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supplierService.updateSupplier(supplierId, supplierRequest));
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> getSupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(supplierService.getSupplierById(supplierId));
    }

    @GetMapping
    public ResponseEntity<Page<SupplierResponse>> getAllSuppliers(
            @RequestParam(required = false) String keyword,
            @PageableDefault(
                    size = 10,
                    page = 0,
                    direction = Sort.Direction.ASC
            )Pageable pageable) {
        return ResponseEntity.ok(supplierService.getAllSupplier(keyword, pageable));
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.noContent().build();
    }

}
