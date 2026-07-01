package com.kevin.be_laptop4you.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long orderId;

    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    private String content;
}
