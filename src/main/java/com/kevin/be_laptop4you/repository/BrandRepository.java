package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByNameBrand(String nameBrand);

    boolean existsBrandByNameBrandEquals(String nameBrand);

    Optional<Brand> getBrandById(Long id);

    Page<Brand> findByNameBrandContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );
}
