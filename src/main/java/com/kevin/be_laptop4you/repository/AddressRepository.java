package com.kevin.be_laptop4you.repository;

import com.kevin.be_laptop4you.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
