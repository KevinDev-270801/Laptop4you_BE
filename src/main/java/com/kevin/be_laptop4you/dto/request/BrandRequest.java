package com.kevin.be_laptop4you.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

    @NotBlank(message = "Tên hãng không được để trống")
    @Size(max = 100)
    private String nameBrand;
}