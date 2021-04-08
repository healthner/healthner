package com.healthner.healthner.controller;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;


@Controller
@RequiredArgsConstructor
public class RegisterController {

//    @Autowired
    private final GymRepository gymRepository;

//    @Autowired
//    public RegisterController(GymRepository gymRepository) {
//        this.gymRepository = gymRepository;
//    }

    @GetMapping(value = "/user-mypage/new-gym")
    public String register(Model model){
        model.addAttribute("gym",new GymForm());
        return "/new-gym";
    }

    @PostMapping(value = "/user-mypage/new-gym")
    public String register(@ModelAttribute GymForm gymForm) {

        Gym gym = gymForm.getEntity(gymForm);
//        ModelMapper modelMapper = new ModelMapper();
//        GymForm newGym = modelMapper.map(gym,GymForm.class);
//        Gym gym = modelMapper.map(gymForm, Gym.class);
        gymRepository.save(gym);
        return "/gym-mypage";
    }
}
