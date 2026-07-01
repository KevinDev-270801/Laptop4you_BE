package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    private Long id;

    private String name;

    private Long customerId;
}