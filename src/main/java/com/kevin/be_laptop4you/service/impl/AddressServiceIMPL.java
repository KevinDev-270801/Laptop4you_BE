package com.kevin.be_laptop4you.service.impl;

import com.kevin.be_laptop4you.dto.request.AddressRequest;
import com.kevin.be_laptop4you.dto.response.AddressResponse;
import com.kevin.be_laptop4you.entity.Address;
import com.kevin.be_laptop4you.entity.Customer;
import com.kevin.be_laptop4you.exception.AppException;
import com.kevin.be_laptop4you.exception.ErrorCode;
import com.kevin.be_laptop4you.mapper.AddressMapper;
import com.kevin.be_laptop4you.repository.AddressRepository;
import com.kevin.be_laptop4you.repository.CustomerRepository;
import com.kevin.be_laptop4you.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceIMPL implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CustomerRepository customerRepository;


    @Override
    public AddressResponse createAddress(AddressRequest addressRequest) {
        Address address = addressMapper.toEntity(addressRequest);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toResponse(savedAddress);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long idAddress, AddressRequest addressRequest) {
        Address address = addressRepository.findById(idAddress).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found address with id: " + idAddress)
        );
        addressMapper.update(addressRequest, address);
        Address savedAddress =  addressRepository.save(address);
        return addressMapper.toResponse(savedAddress);
    }

    @Override
    public Page<AddressResponse> getAllAddressByUserId(Long userId, Pageable pageable) {
        Customer customer = customerRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found customer with id: " + userId)
        );

        Page<Address> addressPage = addressRepository.findAddressesByCustomerId(userId, pageable);

        return addressPage.map(addressMapper::toResponse);
    }

    @Override
    public void deleteAddress(Long idAddress) {
        Address address = addressRepository.findById(idAddress).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found address with id: " + idAddress)
        );
        addressRepository.delete(address);
    }
}
