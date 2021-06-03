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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PurchaseRepository purchaseRepository;

    //예약 생성 전 검증
    private Boolean isExist(Long purchaseId, ReservationDto.ReservRequest reservRequest) {
        Long trainerId = purchaseRepository.findById(purchaseId).orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 구매 상품입니다")).getTrainer().getId();

        return reservationRepository.existsByStartTimeAndTrainerId(reservRequest.getStartTime(), trainerId);
    }

    //예약 생성
    @Transactional
    public Boolean put(ReservationDto.ReservRequest reservRequest, Long purchaseId) {
        if (isExist(purchaseId, reservRequest)) {
            return false;
        }
        ;
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 구매 상품입니다"));
        User user = purchase.getUser();
        Trainer trainer = purchase.getTrainer();
        Reservation reservation = reservRequest.toEntity(user, trainer, purchase);
        reservationRepository.save(reservation);

        return true;
    }

    //예약 수정하기위해 해당예약 초기값 가져오기
    public ReservationDto.ResponseToUser findById(Long id) {
        Reservation find = reservationRepository.findById(id).orElseThrow(
                () -> new ReservationNotFoundException());
        if (!find.getStatus()) {
            return null;
        }
        ReservationDto.ResponseToUser initial = new ReservationDto.ResponseToUser(find);
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
    public void delete(Long id) {
        Long userId = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 예약입니다."))
                .getUser()
                .getId();
        reservationRepository.deleteById(id);
    }

    public Boolean checkStatus(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 예약입니다."));
        if (reservation.getStatus()) return true;
        else return false;
    }

    //user-mypage에 리스트로 뿌려지는 용도
    @Transactional
    public List<ReservationDto.ResponseToUser> findByUserId(Long userId) {
        changeStatus();
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(reservation -> new ReservationDto.ResponseToUser(reservation))
                .collect(Collectors.toList());
    }

    public List<ReservationDto.ResponseToTrainer> findByTrainerId(Long trainerId) {
        return reservationRepository.findByTrainerId(trainerId)
                .stream()
                .map(reservation -> new ReservationDto.ResponseToTrainer(reservation))
                .collect(Collectors.toList());
    }

    //지난 시간의 예약의 상태를 false로 세팅
    private void changeStatus() {
        reservationRepository.findAll()
                .stream()
                .forEach(reservation -> {
                            if (reservation.getEndTime().isBefore(LocalDateTime.now())) {
                                reservation.changeStatus();
                            }
                        }
                );
    }

    public Long findPurchaseId(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옳바르지 않은 예약입니다."))
                .getPurchase().getId();
    }
}