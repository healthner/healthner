package com.healthner.healthner.service;

import com.healthner.healthner.controller.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class GymService {

    private final HttpSession httpSession;
    private final GymRepository gymRepository;

    //Gym 등록
    public String register(GymDto.Request gym){
        if(httpSession.getAttribute("User")== null){
            return "/home";
        }
        Gym newGym = gym.toEntity(gym,(User) httpSession.getAttribute("User"));
        gymRepository.save(newGym);
        return "/gym-mypage";
    }

}
