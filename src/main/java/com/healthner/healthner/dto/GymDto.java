package com.healthner.healthner.dto;

import com.healthner.healthner.domain.Gym;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GymDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SearchRequest {
        private String searchKeyword;
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
}