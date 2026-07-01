package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PromotionResponse {

    private Long id;

    private String name;

    private Integer discountPercent;

    private Boolean active;

    private LocalDate startDate;

    private LocalDate endDate;
}
