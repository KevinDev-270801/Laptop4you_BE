package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierResponse {

    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private Boolean active;
}