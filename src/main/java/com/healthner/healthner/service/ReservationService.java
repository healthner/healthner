package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.exception.handler.ReservationNotFoundException;
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
        Reservation reservation = reservRequest.toEntity(reservRequest);
        reservationRepository.save(reservation);
    }

    //예약 수정하기위해 해당예약 초기값 가져오기
    public ReservationDto.ReservResponse findById(Long id) {
        Reservation find = reservationRepository.findById(id).orElseThrow(
                () -> new ReservationNotFoundException());
        ReservationDto.ReservResponse initial =  new ReservationDto.ReservResponse(find);
        return initial;
    }

    //예약 수정
    @Transactional
    public void update(Long id, ReservationDto.ReservRequest request) {
        Reservation find = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException()); //예약id로 조회됨
        Reservation updateReserv = request.toEntity(request);
        find.updateReservation(updateReserv);
    }

    //예약 삭제
    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    //user-mypage에 리스트로 뿌려지는 용도
    @Transactional
    public List<ReservationDto.ReservResponse> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(reservation -> new ReservationDto.ReservResponse(reservation))
                .collect(Collectors.toList());
    }

    //calendar에 뿌려지는 용도
    public List<ReservationDto.ReservToCal> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> new ReservationDto.ReservToCal(reservation))
                .collect(Collectors.toList());
    }
}