package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.PromotionRequest;
import com.kevin.be_laptop4you.dto.response.PromotionResponse;
import com.kevin.be_laptop4you.entity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    @Mapping(target = "promotionStatus", source = "status")
    Promotion toEntity(PromotionRequest request);

    @Mapping(target = "status", source = "promotionStatus")
    PromotionResponse toResponse(Promotion promotion);

    List<PromotionResponse> toResponseList(List<Promotion> promotions);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(
            PromotionRequest request,
            @MappingTarget Promotion promotion
    );
}
