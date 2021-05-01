package com.healthner.healthner.kakaologin;

import com.healthner.healthner.kakaologin.dto.KakaoUserInfoDto;
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
    @GetMapping("/login")
    public String login(String code, Model model, HttpSession httpSession) {
        KakaoUserInfoDto kakaoUserInfoDto = kakaoService.getProfile(kakaoService.oAuthToken(code));

        String email = kakaoUserInfoDto.getKakao_account().getEmail();
        UserDto.UserInfo userInfo;

        if (userService.findByEmail(email) == null) {
            userInfo = kakaoService.saveKakaoUser(kakaoUserInfoDto);
        } else {
            userInfo = userService.findByEmail(email);
        }

        httpSession.setAttribute("userInfo", userInfo);

        model.addAttribute("user", userInfo);

        return "redirect:home";
    }


    @RequestMapping("logout")
    public String logout(Model model, HttpSession httpSession) {

        UserDto.UserInfo userInfo =  new UserDto.UserInfo(null, null, "로그인을 해주세요", null, null);
        model.addAttribute("user", userInfo);

        httpSession.invalidate();

        return "redirect:home";
    }
}
