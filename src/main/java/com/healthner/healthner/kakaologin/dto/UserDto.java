package com.healthner.healthner.kakaologin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String name;
        private String userImageUrl;
    }
}