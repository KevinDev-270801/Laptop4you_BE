package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.PromotionRequest;
import com.kevin.be_laptop4you.dto.response.PromotionResponse;
import com.kevin.be_laptop4you.entity.Promotion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    Promotion toEntity(PromotionRequest request);

    PromotionResponse toResponse(Promotion promotion);

    List<PromotionResponse> toResponseList(List<Promotion> promotions);
}
