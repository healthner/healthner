package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {



}
