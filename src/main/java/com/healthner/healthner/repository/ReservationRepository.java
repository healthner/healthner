package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long id);

    List<Reservation> findByTrainerId(Long id);

    Boolean existsByStartTimeAndTrainerId(LocalDateTime start, Long trainerId);

}