package com.kevin.be_laptop4you.service.impl;

import com.kevin.be_laptop4you.dto.request.SupplierRequest;
import com.kevin.be_laptop4you.dto.response.SupplierResponse;
import com.kevin.be_laptop4you.entity.Supplier;
import com.kevin.be_laptop4you.exception.AppException;
import com.kevin.be_laptop4you.exception.ErrorCode;
import com.kevin.be_laptop4you.mapper.SupplierMapper;
import com.kevin.be_laptop4you.repository.SupplierRepository;
import com.kevin.be_laptop4you.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceIMPL implements SupplierService {
    private final SupplierMapper supplierMapper;
    private final SupplierRepository supplierRepository;

    private boolean checkEmail(String email){
        return supplierRepository.findByEmail(email).isPresent();
    }
    private boolean checkPhoneNumber(String phoneNumber){
        return supplierRepository.existsByPhoneNumber(phoneNumber);
    }
    private boolean checkName(String name){
        return supplierRepository.existsByName(name);
    }

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {
        boolean existsEmail = checkEmail(request.getEmail());
        boolean existsPhoneNumber = checkPhoneNumber(request.getPhoneNumber());
        boolean existsName = checkName(request.getName());

        if(existsEmail){
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Email already exists");
        }
        if(existsPhoneNumber){
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Phone number already exists");
        }
        if(existsName){
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Name already exists");
        }

        Supplier supplier = supplierMapper.toEntity(request);
        supplier.setActive(true);
        Supplier saved =  supplierRepository.save(supplier);
        return supplierMapper.toResponse(saved);
    }

    @Override
    public SupplierResponse updateSupplier(Long supplierId, SupplierRequest request) {
        Supplier supplierOld = supplierRepository.getSupplierById(supplierId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Supplier not found")
        );

       supplierMapper.updateEntity(request, supplierOld);
       Supplier supplierNew = supplierRepository.save(supplierOld);

        return supplierMapper.toResponse(supplierNew);
    }

    @Override
    public SupplierResponse getSupplierById(Long supplierId) {
        Supplier supplier = supplierRepository.getSupplierById(supplierId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Supplier not found")
        );
        return supplierMapper.toResponse(supplier);
    }

    @Override
    public Page<SupplierResponse> getAllSupplier(String keyword, Pageable pageable) {
        Page<Supplier> suppliers;
        if(keyword == null || keyword.isBlank()){
            suppliers = supplierRepository.findAll(pageable);
        }else {
            suppliers = supplierRepository.getSuppliersByNameContainsIgnoreCase(keyword, pageable);
        }

        return suppliers.map(supplierMapper::toResponse);
    }

    @Override
    public void deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.getSupplierById(supplierId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Supplier not found")
        );
        supplier.setActive(!supplier.getActive());
        supplierRepository.save(supplier);
    }
}
