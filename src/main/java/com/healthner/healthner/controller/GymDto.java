package com.healthner.healthner.controller;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class GymDto {

   @Getter
   @AllArgsConstructor
    public static class Info {
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
       private Long ceoId;

       public Gym toEntity(User ceo) {
           return Gym.createGym(this.getName(), this.getAddress(), this.getContent(), this.getBusinessNumber(), ceo);
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
