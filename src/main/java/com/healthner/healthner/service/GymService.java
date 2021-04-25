package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymService {

    private final UserRepository userRepository;
    private final GymRepository gymRepository;

    public List<GymDto.Response> findByAddress(String searchKeyword) {
        return gymRepository.findByAddressContaining(searchKeyword)
                .stream()
                .map(gym -> new GymDto.Response(gym))
                .collect(Collectors.toList());

    }

    //Gym 등록
    @Transactional
    public Long register(GymDto.Request gym, Long ceoId) {
        User ceo = userRepository.findById(ceoId).get();
        Gym newGym = gym.toEntity(ceo);
        Long saveId = gymRepository.save(newGym).getId();
        return saveId;
    }

}