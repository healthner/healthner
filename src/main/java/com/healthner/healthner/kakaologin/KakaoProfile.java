package com.healthner.healthner.kakaologin;

import lombok.Data;

@Data
public class KakaoProfile { //카카오톡 프로필 정보 받기

    private Integer id;
    private Properties properties;
    private Kakao_account kakao_account;
    private String connected_at;

    @Data
    public class Properties {
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
    }

    @Data
    public class Kakao_account {
        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;

        @Data
        public class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
        }
    }
}
