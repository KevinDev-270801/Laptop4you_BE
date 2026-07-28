package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.PurchaseOrderRequest;
import com.kevin.be_laptop4you.dto.response.PurchaseOrderItemResponse;
import com.kevin.be_laptop4you.dto.response.PurchaseOrderResponse;
import com.kevin.be_laptop4you.entity.PurchaseOrder;
import com.kevin.be_laptop4you.entity.PurchaseOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "purchaseOrderItems", ignore = true)
    PurchaseOrder toEntity(PurchaseOrderRequest purchaseOrderRequest);

    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee.fullName")
    @Mapping(target = "items", source = "purchaseOrderItems")
    @Mapping(target = "status", source = "purchaseOrderStatus")
    PurchaseOrderResponse toResponse(PurchaseOrder purchaseOrder);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    PurchaseOrderItemResponse toItemResponse(PurchaseOrderItem item);
}
