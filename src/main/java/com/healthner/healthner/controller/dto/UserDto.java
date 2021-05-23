package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.interceptor.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class UserDto {

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String userImageUrl;
        private String phoneNumber;
        private Role role;

        public Response(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.userImageUrl = user.getUserImageUrl();
            this.phoneNumber = user.getPhoneNumber();
            this.role = user.getRole();
        }

        public static String UserName(Trainer trainer) {
            return trainer.getUser().getName();
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long id;
        private String phoneNumber;

    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class UserInfo {
        private Long id;
        private String email;
        private String name;
        private String userImageUrl;
        private Role role;

        public UserInfo(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.userImageUrl = user.getUserImageUrl();
            this.role = user.getRole();
        }
    }
}