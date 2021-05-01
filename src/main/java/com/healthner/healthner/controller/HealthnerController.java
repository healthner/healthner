package com.healthner.healthner.controller;

import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.controller.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HealthnerController {

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        UserDto.Response response = (UserDto.Response) session.getAttribute("userInfo");

        if (response == null) {
            response = new UserDto.Response(null, "로그인을 해주세요", null, null);
            model.addAttribute("user", response);
        } else {
            model.addAttribute("user", response);
        }

        return "home";
    }

    @GetMapping(value = "/search")
    public String searchGym(Model model, HttpSession session) {
        UserDto.Response response = (UserDto.Response) session.getAttribute("userInfo");

        if (response == null) {
            response = new UserDto.Response(null, "로그인을 해주세요", null, null);
            model.addAttribute("user", response);
        } else {
            model.addAttribute("user", response);
        }
        return "search";
    }

    @GetMapping(value = "/loginerror")
    public String kakaoError(Model model, HttpSession session) {
        UserDto.Response response = (UserDto.Response) session.getAttribute("userInfo");

        if (response == null) {
            response = new UserDto.Response(null, "로그인을 해주세요", null, null);
            model.addAttribute("user", response);
        } else {
            model.addAttribute("user", response);
        }
        return "loginerror";
    }

    @Auth(role = Role.ADMIN)
    @GetMapping(value = "/adminpage")
    public String getAdminpage() {
        return "adminpage";
    }
}