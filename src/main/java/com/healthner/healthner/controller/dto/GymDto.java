package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GymDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SearchRequest {
        private String searchKeyword;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String name;
        private String address;
        private String content;
        private String businessNumber;
        private Long ceoId;

        public Gym toEntity(User ceo) {
            return Gym.createGym(this.getName(), this.getAddress(), this.getContent(), this.getBusinessNumber(), ceo);
        }
    }

    @Getter
    public static class Response {

        private Long id;
        private String name;
        private String address;
        private String content;
        private String businessNumber;

        public Response(Gym gym) {
            this.id = gym.getId();
            this.name = gym.getName();
            this.address = gym.getAddress();
            this.content = gym.getContent();
            this.businessNumber = gym.getBusinessNumber();
        }
    }

    @Getter
    public static class Form {

        private Long id;
        private String name;
        private String address;
        private String content;
        private String businessNumber;
        private Long ceoId;

        public Form(Gym gym) {
            this.id = gym.getId();
            this.name = gym.getName();
            this.address = gym.getAddress();
            this.content = gym.getContent();
            this.businessNumber = gym.getBusinessNumber();
            this.ceoId = gym.getCeo().getId();
        }
    }
}