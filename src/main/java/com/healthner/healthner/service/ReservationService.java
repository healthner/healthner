package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.domain.Purchase;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.exception.handler.ReservationNotFoundException;
import com.healthner.healthner.repository.PurchaseRepository;
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
    private final ReservationRepository reservationRepository;
    private final PurchaseRepository purchaseRepository;

    //예약 생성 전 검증
    public Boolean isEmpty(Long purchaseId){
         Boolean isExist = reservationRepository.findByPurchaseId(purchaseId)
                 .isEmpty();
         return isExist;
    }

    //예약 생성
    @Transactional
    public Long put(ReservationDto.ReservRequest reservRequest, Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(()-> new IllegalArgumentException("옳바르지 않은 구매 상품입니다"));
        User user = purchase.getUser();
        Trainer trainer = purchase.getTrainer();
        Reservation reservation = reservRequest.toEntity(user, trainer, purchase);
        reservationRepository.save(reservation);
        Long userId = reservation.getUser().getId();

        return userId;
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
    public Long update(Long id, ReservationDto.ReservRequest request) {
        Reservation find = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException()); //예약id로 조회됨
        User user = find.getUser();
        Trainer trainer = find.getTrainer();
        Purchase purchase = find.getPurchase();
        Reservation updateReserv = request.toEntity(user, trainer, purchase);
        find.updateReservation(updateReserv);
        Long userId = updateReserv.getUser().getId();
        return userId;
    }

    //예약 삭제
    @Transactional
    public Long delete(Long id) {
        Long userId = reservationRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("옳바르지 않은 예약입니다."))
                .getUser()
                .getId();
        reservationRepository.deleteById(id);

        return userId;
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