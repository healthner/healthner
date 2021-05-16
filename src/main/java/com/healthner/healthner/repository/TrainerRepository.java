package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByUserId(Long id);

    List<Trainer> findByGymId(Long id);
}