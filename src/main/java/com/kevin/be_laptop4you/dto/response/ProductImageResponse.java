package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageResponse {

    private Long id;

    private String imageUrl;

    private Long productId;
}