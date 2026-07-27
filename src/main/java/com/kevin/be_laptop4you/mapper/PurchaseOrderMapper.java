package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.PurchaseOrderRequest;
import com.kevin.be_laptop4you.entity.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "purchaseOrderItems", ignore = true)
    PurchaseOrder toEntity(PurchaseOrderRequest purchaseOrderRequest);
}
