package com.healthner.healthner.repository;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<ReservationDto.ReservRequest> findByUserId(Long id);



}
