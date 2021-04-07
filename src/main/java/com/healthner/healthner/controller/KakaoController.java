package com.healthner.healthner.controller;

import com.healthner.healthner.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    //카카오 로그인
    @GetMapping(value = "/login")
    public String login(@RequestParam(name = "code") String code, Model model) {
        System.out.println("#########" + code);
        String access_Token = kakaoService.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        System.out.println("###profile_image#### : " + userInfo.get("profile_image"));
        model.addAttribute("userId", userInfo.get("nickname"));
//        return "/body-header";
        return "redirect:/home";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        kakaoService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        return "index";
    }
}
