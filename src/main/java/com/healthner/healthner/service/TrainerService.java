package com.healthner.healthner.service;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.dto.TrainerDto;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final GymRepository gymRepository;

    @Transactional
    public Long save(TrainerDto.Form form, User user) {
        Gym gym = gymRepository.findById(form.getGymId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 gym id 입니다."));

        return trainerRepository.save(form.toEntity(gym, user)).getId();
    }
}