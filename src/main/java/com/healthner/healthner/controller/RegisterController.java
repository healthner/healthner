package com.healthner.healthner.controller;

import com.healthner.healthner.domain.User;
import com.healthner.healthner.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final GymService gymService;


    @GetMapping(value = "/user-mypage/new-gym")
    public String register(Model model){
        model.addAttribute("gym",new GymDto.Request());
        return "/new-gym";
    }

    @PostMapping(value = "/user-mypage/new-gym")
    public String register(@RequestBody GymDto.Request gymDto, @SessionAttribute("User") User ceo) {
        return gymService.register(gymDto, ceo);
    }
}
