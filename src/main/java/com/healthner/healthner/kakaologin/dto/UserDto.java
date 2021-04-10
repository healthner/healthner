package com.healthner.healthner.kakaologin.dto;

import com.healthner.healthner.domain.User;
import com.healthner.healthner.interceptor.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private String userImageUrl;
    }

    @Getter
    @AllArgsConstructor
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