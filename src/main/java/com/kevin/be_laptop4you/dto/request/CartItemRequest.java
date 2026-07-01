package com.kevin.be_laptop4you.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

    @NotNull
    private Long productId;

    @Positive(message = "Số lượng phải lớn hơn 0")
    private Integer quantity;
}
