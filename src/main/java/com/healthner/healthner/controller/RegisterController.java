package com.healthner.healthner.controller;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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
    public String register(@ModelAttribute GymDto.Request gymDto) {
        return gymService.register(gymDto);
    }
}
