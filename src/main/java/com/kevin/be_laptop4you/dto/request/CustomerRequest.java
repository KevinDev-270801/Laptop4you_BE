package com.kevin.be_laptop4you.dto.request;


import com.kevin.be_laptop4you.enums.GenderName;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerRequest {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
            regexp = "^0\\d{9}$",
            message = "Số điện thoại không hợp lệ"
    )
    private String phone;

    @Past(message = "Ngày sinh phải nhỏ hơn hiện tại")
    private LocalDate birthDate;

    @NotNull(message = "Giới tính không được để trống")
    private String gender;

    @NotNull(message = "Account không được để trống")
    private Long accountId;
}
