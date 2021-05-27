package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Remain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RemainRepository extends JpaRepository<Remain, Long> {

    List<Remain> findByUserId(Long id);

    @Query("select id from Remain WHERE purchase.id = :purchaseId")
    Long findByPurchaseId(@Param("purchaseId") Long purchaseId);

}
