package com.kevin.be_laptop4you.dto.response;


import com.kevin.be_laptop4you.enums.PurchaseOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderResponse {
    private Long id;

    private Long employeeId;

    private String employeeName;

    private Long supplierId;

    private String supplierName;

    private LocalDateTime importDate;

    private BigDecimal totalAmount;

    private String note;

    private PurchaseOrderStatus status;

    private List<PurchaseOrderItemResponse> items;
}
