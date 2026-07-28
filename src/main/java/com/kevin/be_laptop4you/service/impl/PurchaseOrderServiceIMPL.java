package com.kevin.be_laptop4you.service.impl;

import com.kevin.be_laptop4you.dto.request.PurchaseOrderItemRequest;
import com.kevin.be_laptop4you.dto.request.PurchaseOrderRequest;
import com.kevin.be_laptop4you.dto.response.PurchaseOrderResponse;
import com.kevin.be_laptop4you.entity.*;
import com.kevin.be_laptop4you.enums.ProductStatus;
import com.kevin.be_laptop4you.enums.PurchaseOrderStatus;
import com.kevin.be_laptop4you.exception.AppException;
import com.kevin.be_laptop4you.exception.ErrorCode;
import com.kevin.be_laptop4you.mapper.PurchaseOrderMapper;
import com.kevin.be_laptop4you.repository.EmployeeRepository;
import com.kevin.be_laptop4you.repository.ProductRepository;
import com.kevin.be_laptop4you.repository.PurchaseOrderRepository;
import com.kevin.be_laptop4you.repository.SupplierRepository;
import com.kevin.be_laptop4you.service.PurchaseOrderService;
import com.kevin.be_laptop4you.utils.SyncProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceIMPL implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final EmployeeRepository employeeRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    @Transactional
    public PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request) {
        Supplier supplier = supplierRepository.getSupplierById(request.getSupplierId()).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Không tìm thấy nhà cung cấp")
        );
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Không tìm thấy nhân viên")
        );

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .supplier(supplier)
                .employee(employee)
                .note(request.getNote())
                .purchaseOrderStatus(PurchaseOrderStatus.DRAFT)
                .totalAmount(BigDecimal.ZERO)
                .build();
//      1: Nhap hang san pham moi


//      2: Nhap san pham da ton tai trong he thong
        addItemToPurchaseOrder(purchaseOrder, request.getItems());

        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);
        return purchaseOrderMapper.toResponse(saved);
    }

    private void addItemToPurchaseOrder(PurchaseOrder purchaseOrder, List<PurchaseOrderItemRequest> itemRequestList) {
        validateDuplicateProduct(itemRequestList);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(PurchaseOrderItemRequest itemRequest : itemRequestList) {
            Product product = productRepository.getProductById(itemRequest.getProductId()).orElseThrow(
                    () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Không tìm thấy sản phẩm ID: "
                            + itemRequest.getProductId())
            );
            BigDecimal subtotal = itemRequest.getUnitPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            PurchaseOrderItem purchaseOrderItem = PurchaseOrderItem.builder()
                    .product(product)
                    .subtotal(subtotal)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(itemRequest.getUnitPrice())
                    .build();

            purchaseOrder.addItem(purchaseOrderItem);
            totalAmount = totalAmount.add(subtotal);
        }
        purchaseOrder.setTotalAmount(totalAmount);
    }

    private void validateDuplicateProduct(List<PurchaseOrderItemRequest> items) {
        Set<Long> productIds = new HashSet<>();
        for (PurchaseOrderItemRequest item : items) {
            boolean isAdded = productIds.add(item.getProductId());
            if (!isAdded) {
                throw new AppException(ErrorCode.DATA_CONFLICT,"Sản phẩm ID " + item.getProductId() + " bị lặp trong phiếu nhập");
            }
        }
    }

    @Override
    @Transactional
    public PurchaseOrderResponse updateDraft(Long purchaseOrderId, PurchaseOrderRequest request) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found purchase order with id: " + purchaseOrderId)
        );
        Supplier supplier = supplierRepository.getSupplierById(request.getSupplierId()).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Không tìm thấy nhà cung cấp")
        );
        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Không tìm thấy nhân viên")
        );

        if (purchaseOrder.getPurchaseOrderStatus() != PurchaseOrderStatus.DRAFT) {
            throw new AppException(ErrorCode.BUSINESS_ERROR, "Chỉ được sửa khi phiếu đang ở trạng thái DRAFT");
        }
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setEmployee(employee);
        purchaseOrder.setNote(request.getNote());
        purchaseOrder.clearItems();

        addItemToPurchaseOrder(purchaseOrder, request.getItems());
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);

        return purchaseOrderMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PurchaseOrderResponse confirmPurchaseOrder(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found purchase order with id: " + purchaseOrderId)
        );
        if (purchaseOrder.getPurchaseOrderStatus() != PurchaseOrderStatus.DRAFT) {
            throw new AppException(ErrorCode.BUSINESS_ERROR, "Chỉ được xác nhận khi phiếu đang ở trạng thái DRAFT");
        }
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        return purchaseOrderMapper.toResponse(purchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderResponse completePurchaseOrder(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found purchase order with id: " + purchaseOrderId)
        );

        if (purchaseOrder.getPurchaseOrderStatus() != PurchaseOrderStatus.PENDING) {
            throw new AppException(ErrorCode.BUSINESS_ERROR, "Chỉ được hoàn tất khi phiếu đang ở trạng thái PENDING");
        }

        if(purchaseOrder.getPurchaseOrderItems().isEmpty()) {
            throw new AppException(ErrorCode.BUSINESS_ERROR, "Phiếu nhập không có sản phẩm");
        }
        // loop cong quantity cho tung product
        for (PurchaseOrderItem item : purchaseOrder.getPurchaseOrderItems()) {
            Product product = productRepository.getProductById(item.getProduct().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Không tìm thấy sản phẩm ID: "+item.getProduct().getId())
            );
            int currentQuantity = product.getQuantity() == null ? 0 : product.getQuantity();
            product.setQuantity(currentQuantity + item.getQuantity());

            if(product.getProductStatus() != ProductStatus.DISABLED){
                SyncProductStatus.syncProductStatusWithQuantity(product);
            }
        }
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.COMPLETED);
        purchaseOrder.setImportDate(LocalDateTime.now());
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);

        return purchaseOrderMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PurchaseOrderResponse cancelPurchaseOrder(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found purchase order with id: " + purchaseOrderId)
        );
        if (purchaseOrder.getPurchaseOrderStatus() == PurchaseOrderStatus.COMPLETED){
            throw new AppException(ErrorCode.BUSINESS_ERROR, "Không thể hủy phiếu đã nhập kho");
        }
        if (purchaseOrder.getPurchaseOrderStatus() == PurchaseOrderStatus.CANCELLED){
            throw new AppException(ErrorCode.BUSINESS_ERROR, "Phiếu này đã được hủy trước đó");
        }

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.CANCELLED);
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);

        return purchaseOrderMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderResponse getPurchaseOrderById(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).orElseThrow(
                () -> new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Not found purchase order with id: " + purchaseOrderId)
        );
        return purchaseOrderMapper.toResponse(purchaseOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderResponse> getAllPurchaseOrders(PurchaseOrderStatus status, Pageable pageable) {
        Page<PurchaseOrder> purchaseOrders;

        if (status == null) {
            purchaseOrders = purchaseOrderRepository.findAll(pageable);
        } else {
            purchaseOrders = purchaseOrderRepository.findByPurchaseOrderStatus(status, pageable);
        }

        return purchaseOrders.map(purchaseOrderMapper::toResponse);
    }
}
