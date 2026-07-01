package com.kevin.be_laptop4you.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PromotionRequest {

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer discountPercent;

    @NotNull
    private Boolean active;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}