package com.kevin.be_laptop4you.dto.response;

import com.kevin.be_laptop4you.enums.OrderStatus;
import com.kevin.be_laptop4you.enums.PaymentMethod;
import com.kevin.be_laptop4you.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private String note;

    private BigDecimal totalAmount;

    private String shippingAddress;

    private LocalDateTime orderDate;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private OrderStatus orderStatus;

    private Long customerId;

    private List<OrderItemResponse> orderItems;
}
