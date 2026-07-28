package com.kevin.be_laptop4you.service;

import com.kevin.be_laptop4you.dto.request.PurchaseOrderRequest;
import com.kevin.be_laptop4you.dto.response.PurchaseOrderResponse;
import com.kevin.be_laptop4you.enums.PurchaseOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseOrderService {
    PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request);

    PurchaseOrderResponse updateDraft(Long purchaseOrderId, PurchaseOrderRequest request);

    PurchaseOrderResponse confirmPurchaseOrder(Long purchaseOrderId);

    PurchaseOrderResponse completePurchaseOrder(Long purchaseOrderId);

    PurchaseOrderResponse cancelPurchaseOrder(Long purchaseOrderId);

    PurchaseOrderResponse getPurchaseOrderById(Long purchaseOrderId);

    Page<PurchaseOrderResponse> getAllPurchaseOrders(PurchaseOrderStatus status, Pageable pageable);
}
