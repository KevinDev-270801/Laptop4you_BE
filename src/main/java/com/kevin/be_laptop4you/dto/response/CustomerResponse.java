package com.kevin.be_laptop4you.dto.response;


import com.kevin.be_laptop4you.enums.GenderName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CustomerResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private LocalDate birthDate;

    private GenderName gender;

    private Long accountId;

    private Long cartId;

    private List<AddressResponse> addresses;
}
