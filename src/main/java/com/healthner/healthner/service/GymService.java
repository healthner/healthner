package com.healthner.healthner.service;

import com.healthner.healthner.controller.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    //Gym 등록
    public String register(GymDto.Request gym, User ceo) {
        if(ceo == null) {
            //추가적인 예외 처리 필요함.
            return "/home";
        }
        Gym newGym = gym.toEntity(gym, ceo);
        gymRepository.save(newGym);
        return "/gym-mypage";
    }

}
