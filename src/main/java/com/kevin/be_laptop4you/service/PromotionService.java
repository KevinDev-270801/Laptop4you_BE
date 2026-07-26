package com.kevin.be_laptop4you.service;

import com.kevin.be_laptop4you.dto.request.PromotionRequest;
import com.kevin.be_laptop4you.dto.response.PromotionResponse;
import com.kevin.be_laptop4you.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionService {
    PromotionResponse createPromotion(PromotionRequest promotionRequest );
    PromotionResponse updatePromotion(Long id,  PromotionRequest promotionRequest );
    PromotionResponse getPromotionById(Long id);
    Page<PromotionResponse> getAllPromotions(String keyword ,Pageable pageable);
    void deletePromotion(Long id);
}
