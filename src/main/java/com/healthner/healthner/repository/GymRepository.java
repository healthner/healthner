package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {

}
