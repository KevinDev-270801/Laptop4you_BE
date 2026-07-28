package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.PurchaseOrder;
import com.kevin.be_laptop4you.enums.PurchaseOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Page<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus status, Pageable pageable);
}
