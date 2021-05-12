package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym, Long> {

    List<Gym> findByAddressContaining(String address);
    Optional<Gym> findByCeoId(Long ceoId);

}