package com.kevin.be_laptop4you.dto.response;

import com.kevin.be_laptop4you.enums.PromotionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PromotionResponse {

    private Long id;

    private String name;

    private Integer discountPercent;

    private PromotionStatus status;

    private LocalDate startDate;

    private LocalDate endDate;
}
