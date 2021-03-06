package com.healthner.healthner.repository;

import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserIdAndProductType(Long id, ProductType productType);

    @Query(value = "select user.id from Purchase where gym.id = :thisGymId and user.id = :userId")
    Long findByGymIdAndUserId(@Param("userId") Long userId, @Param("thisGymId") Long id);

    Boolean existsByUserIdAndProductId(Long userId, Long productId);
}
