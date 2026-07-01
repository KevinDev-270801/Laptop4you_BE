package com.kevin.be_laptop4you.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255)
    private String detail;

    @NotNull(message = "Customer không được để trống")
    private Long customerId;
}
