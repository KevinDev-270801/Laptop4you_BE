package com.kevin.be_laptop4you.service.impl;

import com.kevin.be_laptop4you.dto.request.PromotionRequest;
import com.kevin.be_laptop4you.dto.response.PromotionResponse;
import com.kevin.be_laptop4you.entity.Promotion;
import com.kevin.be_laptop4you.enums.PromotionStatus;
import com.kevin.be_laptop4you.exception.AppException;
import com.kevin.be_laptop4you.exception.ErrorCode;
import com.kevin.be_laptop4you.mapper.PromotionMapper;
import com.kevin.be_laptop4you.repository.PromotionRepository;
import com.kevin.be_laptop4you.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PromotionServiceIMPL implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    private PromotionStatus getStatus(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endExclusive =
                promotion.getEndDate().plusDays(1).atStartOfDay();

        if(now.isBefore(promotion.getStartDate().atStartOfDay()))
            return PromotionStatus.UPCOMING;

        if(!now.isBefore(endExclusive)) // now >= endExclusive
            return PromotionStatus.EXPIRED;

        return PromotionStatus.ACTIVE;
    }

    private boolean refreshStatus(Promotion promotion) {
        PromotionStatus newStatus = getStatus(promotion);
        if(newStatus != promotion.getPromotionStatus()){
            promotion.setPromotionStatus(newStatus);
            return true;
        }
        return false;
    }

    private void validateDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new AppException(
                    ErrorCode.VALIDATION_ERROR,
                    "Ngày bắt đầu không được để trống"
            );
        }

        if (endDate == null) {
            throw new AppException(
                    ErrorCode.VALIDATION_ERROR,
                    "Ngày kết thúc không được để trống"
            );
        }
        if(!endDate.isAfter(startDate)){
            throw new AppException(ErrorCode.VALIDATION_ERROR, "Ngày kết thúc phải sau ngày bắt đầu");
        }
    }

    @Override
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest promotionRequest) {
        boolean existsName = promotionRepository.existsByName(promotionRequest.getName());
        if(existsName){
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Promotion already exists !");
        }
        validateDate(promotionRequest.getStartDate(), promotionRequest.getEndDate());
        Promotion promotion = promotionMapper.toEntity(promotionRequest);
        promotion.setPromotionStatus(getStatus(promotion));
        Promotion promotionSaved = promotionRepository.save(promotion);
        return promotionMapper.toResponse(promotionSaved);
    }

    @Override
    @Transactional
    public PromotionResponse updatePromotion(Long id, PromotionRequest promotionRequest) {
        Promotion promotion = promotionRepository.getPromotionById(id).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Promotion id " + id + " not found!")
        );
        validateDate(promotionRequest.getStartDate(), promotionRequest.getEndDate());
        promotionMapper.updateEntity(promotionRequest, promotion);
        promotion.setPromotionStatus(getStatus(promotion));
        Promotion promotionSaved = promotionRepository.save(promotion);
        return promotionMapper.toResponse(promotionSaved);
    }

    @Override
    @Transactional
    public PromotionResponse getPromotionById(Long id) {
        Promotion promotion = promotionRepository.getPromotionById(id).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Promotion id " + id + " not found!")
        );
        if(refreshStatus(promotion)){
            promotionRepository.save(promotion);
        }
        return promotionMapper.toResponse(promotion);
    }

    @Override
    @Transactional
    public Page<PromotionResponse> getAllPromotions(String keyword, Pageable pageable) {
        Page<Promotion> promotionPage ;
        if(keyword == null || keyword.isEmpty()){
            promotionPage = promotionRepository.findAll(pageable);
        }else {
            promotionPage = promotionRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        promotionPage.getContent().forEach(this :: refreshStatus);

        return promotionPage.map(promotionMapper::toResponse);
    }

    @Override
    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.getPromotionById(id).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Promotion id " + id + " not found!")
        );
        promotion.setPromotionStatus(PromotionStatus.DISABLED);
        promotionRepository.save(promotion);
    }
}
