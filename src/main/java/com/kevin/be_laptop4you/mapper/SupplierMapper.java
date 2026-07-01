package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.SupplierRequest;
import com.kevin.be_laptop4you.dto.response.SupplierResponse;
import com.kevin.be_laptop4you.entity.Supplier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier toEntity(SupplierRequest request);

    SupplierResponse toResponse(Supplier supplier);

    List<SupplierResponse> toResponseList(List<Supplier> suppliers);
}
