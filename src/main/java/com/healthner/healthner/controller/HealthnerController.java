package com.healthner.healthner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthnerController {

    @GetMapping(value = "/loginerror")
    public String kakaoError() {
        return "loginerror";
    }
}