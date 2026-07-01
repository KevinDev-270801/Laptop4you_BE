package com.kevin.be_laptop4you.dto.response;

import com.kevin.be_laptop4you.enums.GenderName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private LocalDate birthDate;

    private GenderName gender;

    private Long accountId;
}
