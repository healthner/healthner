package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationSerivce {


        private  final ReservationRepository reservationRepository;

        public Reservation getReservation(Long id) {
            return reservationRepository.findById(id).get();   //get optional 껍질 벗기는용도
        }

        public void put(ReservationDto reservationDto) {
            Reservation reservation = reservationDto.getEntity(reservationDto);
            reservationRepository.save(reservation);
        }

        @Transactional
        public void modify(Long id, Reservation updateReservation) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservation.updateReservation(updateReservation);

        }

        @Transactional
        public void delete(Long id) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservationRepository.delete(reservation);
        }

}
