package com.healthner.healthner.controller;

import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthnerController {

    @GetMapping(value = "/loginerror")
    public String kakaoError() {
        return "loginerror";
    }

    @Auth(role = Role.ADMIN)
    @GetMapping(value = "/adminpage")
    public String getAdminpage() {
        return "adminpage";
    }

}