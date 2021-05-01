package com.healthner.healthner.kakao.dto;

import com.healthner.healthner.domain.Provider;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.interceptor.Role;
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

    public User toEntity() {
        return User.builder()
                .email(this.getKakao_account().getEmail())
                .name(this.getProperties().getNickname())
                .provider(Provider.KAKAO)
//                .userImageUrl(kakaoProfile.getProperties().getProfile_image())
                .role(Role.USER)
                .build();
    }
}
