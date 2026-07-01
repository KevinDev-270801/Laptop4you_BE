package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
