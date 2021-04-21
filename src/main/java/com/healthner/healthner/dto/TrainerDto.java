package com.healthner.healthner.dto;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TrainerDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Form {
        private Long gymId;
        private String career;

        public Trainer toEntity(Gym gym, User user) {
            return Trainer.createTrainer(gym, user, career);
        }
    }
}
