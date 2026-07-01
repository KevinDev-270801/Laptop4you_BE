package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponse {

    private Long id;

    private BigDecimal totalAmount;

    private Integer totalQuantity;

    private List<CartItemResponse> cartItems;
}