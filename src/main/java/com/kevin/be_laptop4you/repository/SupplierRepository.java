package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Supplier> getSupplierById(Long supplierId);
    Page<Supplier> getSuppliersByNameContainsIgnoreCase(String name, Pageable pageable);
}
