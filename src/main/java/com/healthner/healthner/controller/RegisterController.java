package com.healthner.healthner.controller;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.repository.GymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@Controller
public class RegisterController {

    @Autowired
    GymRepository gymRepository;

    @GetMapping(value = "/user-mypage/new-gym")
    public String register(Model model){
        model.addAttribute("gym",new GymForm());
        return "/new-gym";
    }

    @PostMapping(value = "/user-mypage/new-gym")
    public String register(@RequestBody Gym gym){
        Gym newGym = Gym.createGym(gym.getName(), gym.getAddress(), gym.getContent(), gym.getBusinessNumber(),null);
        gymRepository.save(newGym);
        return "/gym-mypage";
    }
}
