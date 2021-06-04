package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.domain.CheckList;
import com.healthner.healthner.domain.CheckListStatus;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
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
        CheckList checkList = request.toEntity(user, gym);
        checkListRepository.save(checkList);
    }

    // 현재 기관의 user 정보 유무 check
    public Boolean existsByGymIdAndUserPhoneNumber(Long gymId, String phoneNumber) {
        return checkListRepository.existsByGymIdAndUserPhoneNumber(gymId, phoneNumber);
    }

    // 출석 상태 변경
    @Transactional
    public void changeCheckStatus(Long gymId, String phoneNumber, String cmd) {
        CheckList checkList = checkListRepository.findByGymIdAndUserPhoneNumber(gymId, phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("체크인이 잘못되었습니다."));

        if (cmd.equals("in")) {
            checkList.statusToIn();
        } else if (cmd.equals("out")) {
            checkList.statusToOut();
        }
    }

    public Boolean existsByUserId(Long userId) {
        return checkListRepository.existsByUserId(userId);
    }

    public List<CheckListDto.Response> findByUserId(Long userId) {
        return checkListRepository.findByUserId(userId)
                .stream()
                .map(checkList -> {
                    Long gymId = checkList.getGym().getId();
                    Long userCount = checkListRepository.countByGymIdAndStatus(gymId, CheckListStatus.IN);
                    Long userTotal = checkListRepository.countByGymId(gymId);
                    return new CheckListDto.Response(checkList, userCount, userTotal);
                })
                .collect(Collectors.toList());
    }
}
