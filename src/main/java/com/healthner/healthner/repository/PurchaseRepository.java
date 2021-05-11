package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    List<Purchase> findByUserId(Long id);

    Boolean existsByGymIdAndUserId(@Param("userId")Long userId, @Param("thisGymId") Long thisGymId);

    @Query(value = "select user.id from Purchase where gym.id = :thisGymId and user.id = :userId")
    List<Long> findByGymIdAndUserId(@Param("userId")Long userId, @Param("thisGymId") Long id);

    List<Purchase> findByGymId(Long id);

}
