package com.healthner.healthner.service;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.controller.dto.TrainerDto;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.TrainerRepository;
import com.healthner.healthner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final GymRepository gymRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(TrainerDto.Form form, Long userId) {
        User user = getUser(userId);
        Gym gym = getGym(form);

        return trainerRepository.save(form.toEntity(gym, user)).getId();
    }

    @Transactional
    public Long update(Long id, Long userId, TrainerDto.Form updateForm) {
        Trainer trainer = getTrainer(id);
        User user = getUser(userId);
        Gym gym = getGym(updateForm);

        trainer.updateTrainer(updateForm.toEntity(gym, user));
        return trainer.getId();
    }

    public TrainerDto.Form findById(Long id) {
        Trainer trainer = getTrainer(id);

        return new TrainerDto.Form(trainer);
    }

    public TrainerDto.Form findByUserId(Long userId) {
        Trainer trainer = trainerRepository.findByUserId(userId).orElse(null);
        return trainer == null ? null : new TrainerDto.Form(trainer);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 user id 입니다."));
    }

    private Gym getGym(TrainerDto.Form form) {
        return gymRepository.findById(form.getGymId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 gym id 입니다."));
    }

    private Trainer getTrainer(Long id) {
        return trainerRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 trainer id 입니다."));
    }
}