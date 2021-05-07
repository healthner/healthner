package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findByGymId(Long id);

    List<Purchase> findByUserId(Long id);
}
