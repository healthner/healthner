package com.healthner.healthner.controller;

import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.kakaologin.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HealthnerController {

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        UserDto.UserInfo userInfo = (UserDto.UserInfo) session.getAttribute("userInfo");

        if (userInfo == null) {
            userInfo = new UserDto.UserInfo(null, null, "로그인을 해주세요", null, null);
            model.addAttribute("user", userInfo);
        } else {
            model.addAttribute("user", userInfo);
        }

        return "home";
    }

    @GetMapping(value = "/search")
    public String searchGym(Model model, HttpSession session) {
        UserDto.UserInfo userInfo = (UserDto.UserInfo) session.getAttribute("userInfo");

        if (userInfo == null) {
            userInfo = new UserDto.UserInfo(null, null, "로그인을 해주세요", null, null);
            model.addAttribute("user", userInfo);
        } else {
            model.addAttribute("user", userInfo);
        }
        return "search";
    }

    @GetMapping(value = "/purchase")
    public String purchase(){
        return "purchase";
    }

    @GetMapping(value = "/gymdetail")
    public String gymDetail(){
        return "gymdetail";
    }




    //출석체크
    @GetMapping(value = "/gym-mypage/check")
    public String check(){
        return "check";
    }


    //마이페이지
    @GetMapping(value = "/trainer-mypage")
    public String trainerMypage(){
        return "trainer-mypage";
    }

    @GetMapping(value = "/user-mypage")
    public String userMypage(){
        return "user-mypage";
    }

    @GetMapping(value = "/gym-mypage")
    public String gymMypage(){
        return "gym-mypage";
    }




    //등록*생성 (시설,트레이너,상품)
    @GetMapping(value = "/user-mypage/new-gym")
    public String newGym(){
        return "new-gym";
    }

    @GetMapping(value = "/user-mypage/new-trainer")
    public String newTrainer(){
        return "new-trainer";
    }

    @GetMapping(value = "/gym-mypage/new-product")
    public String newProductByGym(){
        return "new-products";
    }

    @GetMapping(value = "/trainer-mypage/new-product")
    public String newProductByTrainer(){
        return "new-products";
    }

    @GetMapping(value = "/loginerror")
    public String kakaoError(){
        return "loginerror";
    }

    @Auth(role = Role.ADMIN)
    @GetMapping(value = "/adminpage")
    public String getAdminpage() {
        return "adminpage";
    }

}