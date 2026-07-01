package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
