package com.healthner.healthner.controller;

import com.healthner.healthner.repository.TrainerRepository;
import com.healthner.healthner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    @GetMapping
    public String index() {
        return "redirect:home";
    }

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("trainerCount", trainerRepository.count());

        return "home";
    }
}