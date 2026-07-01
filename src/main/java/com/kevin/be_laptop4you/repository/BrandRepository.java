package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByNameBrand(String nameBrand);
}
