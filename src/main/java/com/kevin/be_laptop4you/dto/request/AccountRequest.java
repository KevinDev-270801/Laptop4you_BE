package com.kevin.be_laptop4you.dto.request;

import com.kevin.be_laptop4you.enums.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    @NotBlank(message = "Username không được để trống")
    @Size(min = 4, max = 50)
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 6, max = 100)
    private String password;

    @NotNull(message = "Role không được để trống")
    private RoleName role;
}
