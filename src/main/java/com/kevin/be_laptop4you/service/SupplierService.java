package com.kevin.be_laptop4you.service;


import com.kevin.be_laptop4you.dto.request.SupplierRequest;
import com.kevin.be_laptop4you.dto.response.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {
    SupplierResponse createSupplier(SupplierRequest request);
    SupplierResponse updateSupplier(Long supplierId, SupplierRequest request);
    SupplierResponse getSupplierById(Long supplierId);
    Page<SupplierResponse> getAllSupplier(
            String keyword,
            Pageable pageable
    );
    void deleteSupplier(Long supplierId);
}
