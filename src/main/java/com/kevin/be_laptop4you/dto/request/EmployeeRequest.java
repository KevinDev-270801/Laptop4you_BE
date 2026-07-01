package com.kevin.be_laptop4you.dto.request;

import com.kevin.be_laptop4you.enums.GenderName;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {

    @NotBlank
    @Size(max = 100)
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^0\\d{9}$")
    private String phone;

    @NotBlank
    private String address;

    @Past
    private LocalDate birthDate;

    @NotNull
    private GenderName gender;

    @NotNull
    private Long accountId;
}
