package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.domain.CheckList;
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
    public Long countByGymId(Long thisGymId) {
        return checkListRepository.countByGymId(thisGymId);
    }

    //출석 객체 생성
    @Transactional
    public Long put(User user, Gym gym) {
        CheckListDto.Request request = new CheckListDto.Request();
        request.setStatus(CheckListStatus.ABSENT);
        CheckList checkList = request.toEntity(user, gym);
        checkListRepository.save(checkList);
        return checkList.getId();
    }
}
