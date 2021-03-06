package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TrainerDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Form {
        private Long id;
        private Long gymId;
        private String career;

        public Form(Trainer trainer) {
            this.id = trainer.getId();
            this.gymId = trainer.getGym().getId();
            this.career = trainer.getCareer();
        }

        public Trainer toEntity(Gym gym, User user) {
            return Trainer.createTrainer(gym, user, career);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Long gymId;
        private String career;
        private String name;

        public Response(Trainer trainer) {
            this.id = trainer.getId();
            this.gymId = trainer.getGym().getId();
            this.career = trainer.getCareer();
            this.name = UserDto.Response.UserName(trainer);
        }
    }
}
