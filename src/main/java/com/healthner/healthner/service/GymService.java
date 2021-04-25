package com.healthner.healthner.service;

import com.healthner.healthner.controller.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class GymService {

    private final UserRepository userRepository;
    private final GymRepository gymRepository;

    //Gym 등록
    @Transactional
    public Long register(GymDto.Request gym, Long ceoId) {
        User ceo = userRepository.findById(ceoId).get();
        Gym newGym = gym.toEntity(ceo);
        Long saveId = gymRepository.save(newGym).getId();
        return saveId;
    }

}
