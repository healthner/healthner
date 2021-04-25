package com.healthner.healthner.controller;

import com.healthner.healthner.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @GetMapping("/user-mypage/new-gym")
    public String register(Model model) {
        model.addAttribute("gym", new GymDto.Request());
        return "/new-gym";
    }

    @PostMapping("/user-mypage/new-gym")
    public String register(@RequestParam GymDto.Request gymDto, /*@SessionAttribute("userId") */Long ceoId) {
        Long saveId = gymService.register(gymDto,ceoId);
        return "/gym-mypage";
    }

    @PostMapping("/new-gym/{gymId}")
    public String modify(@PathVariable Long gymId) {

        return "/new-gym"+gymId;
    }

    @GetMapping("/gym-mypage")
    public String getGym(Long gymId) {
        return "/gym-mypage";
    }
}
