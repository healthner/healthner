package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.interceptor.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {

    @GetMapping("search")
    public String getSearch() {
        return "gym/search";
    }

    @GetMapping("{gymId}/detail")
    public String detail(@PathVariable("gymId") Long id) {
        return "gym/detail";
    }

    @GetMapping("{gymId}/my-page")
    public String myPage(@PathVariable("gymId") Long id) {
        return "gym/mypage";
    }
}