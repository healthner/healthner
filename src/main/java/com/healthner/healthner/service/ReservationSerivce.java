package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

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
        public void modify(Long id, ReservationDto reservationDto) {
            Reservation findReserv = reservationRepository.findById(id).get();
            Reservation updateReserv = reservationDto.getEntity(reservationDto);

            findReserv.updateReservation(updateReserv);
            reservationRepository.save(updateReserv);

        }

        @Transactional
        public void delete(Long id) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservationRepository.delete(reservation);
        }

        @Transactional
        public void selectEventList(Model model) {
                List<Reservation> findReservations = reservationRepository.findAll();
                List<ReservationDto> collect = findReservations.stream()
                        .map(reservation -> new ReservationDto(reservation))
                        .collect(Collectors.toList());
                model.addAttribute("reservations",collect);
       }

        @Transactional
        public List<ReservationDto> selectEventList2(ReservationDto reservationDto) {
            List<Reservation> findReservations = reservationRepository.findAll();
            List<ReservationDto> collect = findReservations.stream()
                    .map(reservation -> new ReservationDto(reservation))
                    .collect(Collectors.toList());
           return collect;
        }
}
