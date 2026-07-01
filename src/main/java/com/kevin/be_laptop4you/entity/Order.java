package com.kevin.be_laptop4you.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kevin.be_laptop4you.enums.OrderStatus;
import com.kevin.be_laptop4you.enums.PaymentMethod;
import com.kevin.be_laptop4you.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String note;
    private BigDecimal totalAmount;
    private String shippingAddress;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // tt thanh toan

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // pt thanh toan

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // trang thai don hang

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>()  ;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>()  ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer  customer;
}
