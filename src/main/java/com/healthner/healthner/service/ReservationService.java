package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.exception.handler.ReservtionNotFoundException;
import com.healthner.healthner.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private  final ReservationRepository reservationRepository;

    //예약 생성
    @Transactional
    public void put(ReservationDto.ReservRequest reservRequest) {
        Reservation reservation = reservRequest.getEntity(reservRequest);
        reservationRepository.save(reservation);
    }

    //예약 수정하기위해 해당예약 초기값 가져오기
    public ReservationDto.ReservResponse findModifyReservation(Long id){
        Reservation find = reservationRepository.findById(id).orElseThrow(() -> new ReservtionNotFoundException());
        ReservationDto.ReservResponse initial =  new ReservationDto.ReservResponse(find);
        return initial;
    }

    //예약 수정
    @Transactional
    public void modify(Long id, ReservationDto.ReservRequest request) {
        Reservation find = reservationRepository.findById(id).orElseThrow(() -> new ReservtionNotFoundException()); //예약id로 조회됨
        Reservation updateReserv = request.getEntity(request);
        find.updateReservation(updateReserv);
    }

    //예약 삭제
    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    //user-mypage에 리스트로 뿌려지는 용도
    @Transactional
    public List<ReservationDto.ReservResponse> getMyEventList(Long userId) {
        List<Reservation> find = reservationRepository.findByUserId(userId);
        List<ReservationDto.ReservResponse> result = find.stream()
                .map(reservation -> new ReservationDto.ReservResponse(reservation))
                .collect(Collectors.toList());
        return result;
    }

    //calendar에 뿌려지는 용도
    public List<ReservationDto.ReservToCal> findAll() {
        List<Reservation> findReservations = reservationRepository.findAll();
        List<ReservationDto.ReservToCal> result = findReservations.stream()
                .map(reservation -> new ReservationDto.ReservToCal(reservation))
                .collect(Collectors.toList());
        return result;
    }
}