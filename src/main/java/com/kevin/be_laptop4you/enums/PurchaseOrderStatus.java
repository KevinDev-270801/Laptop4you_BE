package com.kevin.be_laptop4you.enums;

public enum PurchaseOrderStatus {
    DRAFT,       // Phiếu nháp, chưa cộng kho
    PENDING,     // Chờ xác nhận hoặc chờ nhận hàng
    COMPLETED,   // Đã nhập kho
    CANCELLED    // Đã hủy
}
