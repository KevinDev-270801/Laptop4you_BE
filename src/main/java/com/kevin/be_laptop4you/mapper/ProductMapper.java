package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.ProductRequest;
import com.kevin.be_laptop4you.dto.response.ProductResponse;
import com.kevin.be_laptop4you.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // Câ chỉnh sửa sau
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "purchaseOrderItems", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Product toEntity(ProductRequest request);

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.nameBrand")
    @Mapping(target = "promotionId", source = "promotion.id")
    @Mapping(target = "promotionName", source = "promotion.name")
    @Mapping(target = "status", source = "productStatus")
    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "productStatus", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "purchaseOrderItems", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(ProductRequest request, @MappingTarget Product product);

}
