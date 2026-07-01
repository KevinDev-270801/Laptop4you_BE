package com.kevin.be_laptop4you.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseOrderRequest {

    @NotNull
    private Long supplierId;

    @NotNull
    private Long employeeId;

    @NotNull
    private List<PurchaseOrderItemRequest> items;
}
