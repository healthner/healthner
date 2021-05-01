package com.healthner.healthner.kakaologin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class KakaoUserInfoDto {

    private Long id;
    private Properties properties;
    private Kakao_account kakao_account;

    @Data
    public class Properties {
        private String nickname;
    }

    @Data
    public class Kakao_account {
        private String email;
    }
}
