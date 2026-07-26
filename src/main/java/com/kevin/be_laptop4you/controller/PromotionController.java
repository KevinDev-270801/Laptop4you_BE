package com.kevin.be_laptop4you.controller;

import com.kevin.be_laptop4you.dto.request.PromotionRequest;
import com.kevin.be_laptop4you.dto.response.PromotionResponse;
import com.kevin.be_laptop4you.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<PromotionResponse> createPromotion (@RequestBody @Valid PromotionRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(promotionService.createPromotion(request));
    }

    @GetMapping
    public ResponseEntity<Page<PromotionResponse>> getAllPromotion(@RequestParam(required = false) String keyword,
                                                                   @PageableDefault(
                                                                           size = 10,
                                                                           page = 0,
                                                                           direction = Sort.Direction.ASC
                                                                   )Pageable pageable){
        return ResponseEntity.ok(promotionService.getAllPromotions(keyword, pageable));
    }

    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable Long promotionId){
        return ResponseEntity.ok(promotionService.getPromotionById(promotionId));
    }

    @PutMapping("/{promotionId}")
    public ResponseEntity<PromotionResponse> updatePromotion (@PathVariable Long promotionId,
                                                     @RequestBody @Valid PromotionRequest request){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionService.updatePromotion(promotionId, request));
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long promotionId){
        promotionService.deletePromotion(promotionId);
        return ResponseEntity.noContent().build();
    }


}
