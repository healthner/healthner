package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationSerivce {


        private  final ReservationRepository reservationRepository;


        public Reservation getReservation(Long id) {
            return reservationRepository.findById(id).get();   //get optional 껍질 벗기는용도
        }

        public void put(ReservationDto.ReservRequest reservRequest) {
            Reservation reservation = reservRequest.getEntity(reservRequest);
            reservationRepository.save(reservation);
        }

        @Transactional
        public void modify(Long id, ReservationDto.ReservRequest reservRequest) {
            Reservation findReserv = reservationRepository.findById(id).get();
            Reservation updateReserv = reservRequest.getEntity(reservRequest);

            findReserv.updateReservation(updateReserv);
            reservationRepository.save(updateReserv);

        }

        @Transactional
        public void delete(Long id) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservationRepository.delete(reservation);
        }

        //user-mypage에 리스트로 뿌려지는 용도
        @Transactional
        public void getMyEventList(Long id) {
            Optional<Reservation> findReservations = reservationRepository.findById(id);
                List<ReservationDto.ReservRequest> collect = findReservations.stream()
                        .map(reservation -> new ReservationDto.ReservRequest(reservation))
                        .collect(Collectors.toList());

       }

       //calendar에 뿌려지는 용도
        @Transactional
        public List<ReservationDto.ReservResponse> eventToCalenar(ReservationDto.ReservRequest reservRequest) {
            List<Reservation> findReservations = reservationRepository.findAll();
            List<ReservationDto.ReservResponse> result = findReservations.stream()
                    .map(reservation -> new ReservationDto.ReservRequest(reservation))
                    .map(reservRequest1 -> new ReservationDto.ReservResponse(reservRequest1))
                    .collect(Collectors.toList());
           return result;
        }
}
