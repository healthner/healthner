package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.service.GymService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gym")
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


