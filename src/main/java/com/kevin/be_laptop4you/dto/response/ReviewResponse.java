package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {

    private Long id;

    private Long productId;

    private String productName;

    private Long orderId;

    private Integer rating;

    private String content;
}