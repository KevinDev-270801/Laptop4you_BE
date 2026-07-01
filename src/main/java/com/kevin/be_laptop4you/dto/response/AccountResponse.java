package com.kevin.be_laptop4you.dto.response;

import com.kevin.be_laptop4you.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private Long id;

    private String username;

    private RoleName role;
}
