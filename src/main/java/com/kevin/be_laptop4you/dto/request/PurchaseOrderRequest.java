package com.kevin.be_laptop4you.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderRequest {

    @NotNull(message = "Nhà cung cấp không được để trống")
    private Long supplierId;

    @NotNull(message = "Nhân viên nhập hàng không được để trống")
    private Long employeeId;

    @Size(
            max = 2000,
            message = "Ghi chú không được vượt quá 2000 ký tự"
    )
    private String note;

    @Valid
    @NotEmpty(message = "Phiếu nhập phải có ít nhất một sản phẩm")
    private List<PurchaseOrderItemRequest> items;
}
