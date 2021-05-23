package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.CheckListStatus;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckListService {
    private final CheckListRepository checkListRepository;

    //현재 해당기관의 총 인원 수 리턴
    public Long total(Long gymId) {
        return checkListRepository.countByGymId(gymId);
    }

    //현재 이용자 수
    public Long users(Long gymId) {
        return checkListRepository.countByGymIdAndStatus(gymId, CheckListStatus.IN);
    }

    //출석 객체 생성
    @Transactional
    public void put(User user, Gym gym) {
        CheckListDto.Request request = new CheckListDto.Request();
        request.setStatus(CheckListStatus.OUT);
        CheckList checkList = request.toEntity(user, gym);
        checkListRepository.save(checkList);
    }

    // 회원 상태 확인
    public boolean check(String phone, Long gymId) {
        CheckList userStatus = checkListRepository.findByUserPhoneNumberAndGymId(phone, gymId).orElseThrow(() -> new IllegalArgumentException("현재 회원 상태를 확인할 수 없습니다."));
        return userStatus.getStatus() == CheckListStatus.IN;
    }

    //출석
    @Transactional
    public void checkIn(String phone, Long gymId) {
        CheckList checkList = checkListRepository.findByUserPhoneNumberAndGymId(phone, gymId).orElseThrow(() -> new IllegalArgumentException("체크인이 잘못되었습니다."));
        checkList.chageStatus(CheckListStatus.IN);
    }

    //결석
    @Transactional
    public void checkOut(String phone, Long gymId) {
        CheckList checkList = checkListRepository.findByUserPhoneNumberAndGymId(phone, gymId).orElseThrow(() -> new IllegalArgumentException("체크아웃이 잘못되었습니다."));
        checkList.chageStatus(CheckListStatus.OUT);
    }

    public Boolean existsByUserId(Long userId) {
        return checkListRepository.existsByUserId(userId);
    }
}
