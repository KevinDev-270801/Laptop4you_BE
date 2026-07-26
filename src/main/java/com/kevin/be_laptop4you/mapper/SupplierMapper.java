package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.SupplierRequest;
import com.kevin.be_laptop4you.dto.response.SupplierResponse;
import com.kevin.be_laptop4you.entity.Supplier;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "active", ignore = true)
    Supplier toEntity(SupplierRequest request);

    SupplierResponse toResponse(Supplier supplier);

    List<SupplierResponse> toResponseList(List<Supplier> suppliers);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(
            SupplierRequest request,
            @MappingTarget Supplier supplier
    );
}
