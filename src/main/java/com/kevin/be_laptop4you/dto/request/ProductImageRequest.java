package com.kevin.be_laptop4you.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageRequest {

    @NotBlank(message = "Image URL không được để trống")
    private String imageUrl;

    @NotNull(message = "Product không được để trống")
    private Long productId;
}