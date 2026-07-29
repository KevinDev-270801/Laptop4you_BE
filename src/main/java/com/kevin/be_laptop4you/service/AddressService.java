package com.kevin.be_laptop4you.service;

import com.kevin.be_laptop4you.dto.request.AddressRequest;
import com.kevin.be_laptop4you.dto.response.AddressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    AddressResponse createAddress(AddressRequest addressRequest);
    AddressResponse updateAddress(Long idAddress, AddressRequest addressRequest);
    Page<AddressResponse> getAllAddressByUserId(Long userId, Pageable pageable);
    void deleteAddress(Long idAddress);




}
