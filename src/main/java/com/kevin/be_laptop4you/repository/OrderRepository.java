package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Order;
import com.kevin.be_laptop4you.enums.OrderStatus;
import com.kevin.be_laptop4you.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByPaymentStatus(PaymentStatus status);
}
