package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Promotion;
import com.kevin.be_laptop4you.enums.PromotionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Page<Promotion> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Optional<Promotion> getPromotionById(Long id);
    boolean existsByName(String name);
}
