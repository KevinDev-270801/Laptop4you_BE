package com.kevin.be_laptop4you.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderResponse {

    private Long id;

    private LocalDateTime importDate;

    private Long supplierId;

    private String supplierName;

    private Long employeeId;

    private String employeeName;

    private List<PurchaseOrderItemResponse> items;
}
