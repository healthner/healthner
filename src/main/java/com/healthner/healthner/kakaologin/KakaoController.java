package com.healthner.healthner.kakaologin;

import com.healthner.healthner.kakaologin.dto.UserDto;
import com.healthner.healthner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    private final UserService userService;

    //카카오 로그인
    @GetMapping(value = "/login")
    public String login(String code, Model model, HttpSession httpSession) {
        KakaoProfile profile = kakaoService.getProfile(kakaoService.oAuthToken(code));

        String email = profile.getKakao_account().getEmail();
        UserDto.UserInfo userInfo;

        if (userService.findByEmail(email) == null) {
            userInfo = kakaoService.saveKakaoUser(profile);
        } else {
            userInfo = userService.findByEmail(email);
        }

        httpSession.setAttribute("userInfo", userInfo);

        model.addAttribute("user", userInfo);
        return "kakaoLogin";
    }


    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        kakaoService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        return "index";
    }
}
