package com.healthner.healthner.kakao;

import com.healthner.healthner.domain.User;
import com.healthner.healthner.kakao.dto.KakaoUserInfoDto;
import com.healthner.healthner.controller.dto.UserDto;
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
        UserDto.Response response = null;

        if (userService.findByEmail(email) == null) { // 존재하지 않는 user 일 때
            User user = kakaoService.getUser(kakaoUserInfoDto);
            userService.join(user);
            response = userService.findByEmail(email); // 다시 조회
        } else { // 존재하는 user 일 때
            response = userService.findByEmail(email); // 기존에 우리가 가지고 있는 user
            User user = kakaoService.getUser(kakaoUserInfoDto); // update된 User 정보
            userService.update(response.getId(), user);
            response = userService.findByEmail(email); // 다시 조회
        }

        httpSession.setAttribute("userInfo", response);

        model.addAttribute("user", response);

        return "redirect:home";
    }


    @RequestMapping("logout")
    public String logout(Model model, HttpSession httpSession) {

        UserDto.Response response =  new UserDto.Response(null, "로그인을 해주세요", null, null);
        model.addAttribute("user", response);

        httpSession.invalidate();

        return "redirect:home";
    }
}
