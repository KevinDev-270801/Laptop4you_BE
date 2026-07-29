package com.kevin.be_laptop4you.controller;

import com.kevin.be_laptop4you.dto.request.AddressRequest;
import com.kevin.be_laptop4you.dto.response.AddressResponse;
import com.kevin.be_laptop4you.repository.AddressRepository;
import com.kevin.be_laptop4you.service.AddressService;
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
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest addressRequest){
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(addressService.createAddress(addressRequest));
    }

    @PutMapping("/{idAddress}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long idAddress,
                                                         @RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok().body(addressService.updateAddress(idAddress, addressRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<AddressResponse>> getAddress(@PathVariable Long userId,
                                                            @PageableDefault(
                                                                    size = 10,
                                                                    page = 0,
                                                                    direction = Sort.Direction.ASC
                                                            ) Pageable pageable){
        return ResponseEntity.ok(addressService.getAllAddressByUserId(userId, pageable));
    }

    @DeleteMapping("/{idAddress}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long idAddress){
        addressService.deleteAddress(idAddress);
        return ResponseEntity.noContent().build();
    }
}
