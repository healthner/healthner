package com.healthner.healthner.kakaologin;

import com.healthner.healthner.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    //카카오 로그인
    @GetMapping(value = "/login")
    public String login(String code, Model model) {
        User user = kakaoService.saveKakaoUser(kakaoService.getProfile(
                kakaoService.oAuthToken(code)));
        model.addAttribute("user", user);
        return "redirect:/home";

//        //인가 코드를 받은 뒤, 인가 코드로 액세스 토큰과 리프레시 토큰을 발급받는 API입니다.
//
//        //HttpHeader 오브젝트 생성
//        RestTemplate rt = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "f59edafa1084c3f6d25a24aa88f9e0b7");
//        params.add("redirect_uri", "http://localhost:8080/login");
//        params.add("code", code);
//
//        //HttpHeader와 HttpBody를 하나의 오브젝트에 담는거
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(params, headers);
//
//        // Http 요청 - post 방식으로 => 응답
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        RetKakaoAuth oauthToken = null;
//
//        try {
//            oauthToken = objectMapper.readValue(response.getBody(), RetKakaoAuth.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        //HttpHeader 오브젝트 생성
//        RestTemplate rt2 = new RestTemplate();
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
//                new HttpEntity<>(headers2);
//
//        // Http 요청 - post 방식으로 => 응답
//        ResponseEntity<String> response2 = rt2.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest2,
//                String.class
//        );
//
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        KakaoProfile kakaoProfile = null;
//
//        try {
//            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return response2.getBody();

//        System.out.println("#########" + code);
//        String access_Token = kakaoService.getAccessToken(code);
//        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
//        System.out.println("###access_Token#### : " + access_Token);
//        System.out.println("###userInfo#### : " + userInfo.get("email"));
//        System.out.println("###nickname#### : " + userInfo.get("nickname"));
//        System.out.println("###profile_image#### : " + userInfo.get("profile_image"));
//        model.addAttribute("userId", userInfo.get("nickname"));
//        return "/body-header";
//        return "redirect:/home";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        kakaoService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        return "index";
    }
}
