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
        //예약 생성
        public void put(ReservationDto.ReservRequest reservRequest) {
            Reservation reservation = reservRequest.getEntity(reservRequest);
            reservationRepository.save(reservation);
        }

        //예약 수정하기위해 해당예약 초기값 가져오기
        public ReservationDto.ReservRequest findModifyReservation(Long id){
            Reservation find = reservationRepository.findById(id).get();
            ReservationDto.ReservRequest initial =  new ReservationDto.ReservRequest(find);
            return initial;
        }

        //예약 수정
        @Transactional
        public void modify(Long id, ReservationDto.ReservRequest request) {
            Reservation find = reservationRepository.findById(id).get(); //예약id로 조회됨
            Reservation updateReserv = request.getEntity(request);
            find.updateReservation(updateReserv);
        }

        //예약 삭제
        @Transactional
        public void delete(Long id) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservationRepository.delete(reservation);
        }

        //user-mypage에 리스트로 뿌려지는 용도
        @Transactional
        public List<ReservationDto.ReservRequest> getMyEventList(Long userId) {
            return reservationRepository.findByUserId(userId);

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
