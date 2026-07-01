package com.kevin.be_laptop4you.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseOrderItemRequest {

    @NotNull
    private Long productId;

    @Positive
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal unitPrice;
}