package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.AddressRequest;
import com.kevin.be_laptop4you.dto.response.AddressResponse;
import com.kevin.be_laptop4you.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressRequest addressRequest);

    @Mapping(target = "customerId" , source = "customer.id")
    AddressResponse toResponse(Address address);

    void update(AddressRequest addressRequest, @MappingTarget Address address);
}
