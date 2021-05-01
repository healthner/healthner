package com.healthner.healthner.kakao;

import lombok.Data;

@Data
public class RetKakaoAuth { // Social Login 에 필요한 Model 객체 생성
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
