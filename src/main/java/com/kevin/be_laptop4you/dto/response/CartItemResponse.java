package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String thumbnail;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal subtotal;
}
