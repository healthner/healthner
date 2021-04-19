package com.healthner.healthner.kakaologin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthner.healthner.domain.Provider;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.kakaologin.dto.UserDto;
import com.healthner.healthner.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Builder
public class KakaoService {

    private final UserService userService;

    public RetKakaoAuth oAuthToken(String code) {

        //인가 코드를 받은 뒤, 인가 코드로 액세스 토큰과 리프레시 토큰을 발급받는 API

        //HttpHeader 오브젝트 생성
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f59edafa1084c3f6d25a24aa88f9e0b7");
        params.add("redirect_uri", "http://localhost:8080/login");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담는거
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청 - post 방식으로 => 응답
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        RetKakaoAuth oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), RetKakaoAuth.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oauthToken;
    }

    /**
     * 인증 받은 토큰으로 프로필 정보 받기
     *
     * @param oauthToken
     */
    public KakaoProfile getProfile(RetKakaoAuth oauthToken) {
        //HttpHeader 오브젝트 생성
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        // Http 요청 - post 방식으로 => 응답
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoProfile;
    }

    // 카카오에서 받은 정보 User에 채우고 디비에 저장
    public UserDto.Response saveKakaoUser(KakaoProfile kakaoProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        User user = User.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .name(kakaoProfile.getKakao_account().getProfile().getNickname())
                .provider(Provider.KAKAO)
                .userImageUrl(kakaoProfile.getProperties().getProfile_image())
                .password(password.toString())
                .build();

        userService.join(user);
        return new UserDto.Response(user.getName(), user.getUserImageUrl());
    }

    public void kakaoLogout(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
