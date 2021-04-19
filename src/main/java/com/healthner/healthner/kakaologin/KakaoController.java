package com.healthner.healthner.kakaologin;

import com.healthner.healthner.kakaologin.dto.UserDto;
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
        UserDto.Response user = kakaoService.saveKakaoUser(kakaoService.getProfile(
                kakaoService.oAuthToken(code)));
        model.addAttribute("user", user);
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
