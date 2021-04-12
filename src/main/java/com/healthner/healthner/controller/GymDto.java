package com.healthner.healthner.controller;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import lombok.*;


public class GymDto {

   @Getter
   @AllArgsConstructor
    public static class Info{
        private int id;
        private String name;
        private String address;
        private String content;
        private String businessNumber;
        private User ceo;
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

       public Gym toEntity(GymDto.Request dto,User ceo){
           return Gym.createGym(dto.getName(),dto.getAddress(),dto.getContent(),dto.getBusinessNumber(),ceo);
       }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String name;
        private String address;
        private String content;
        private String businessNumber;
        private User ceo;
    }

}
