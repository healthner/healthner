package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Remain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemainRepository extends JpaRepository<Remain, Long> {
    
    List<Remain> findByUserId(Long id);
}
