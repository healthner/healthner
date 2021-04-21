package com.healthner.healthner.controller;

import com.healthner.healthner.domain.User;
import com.healthner.healthner.dto.TrainerDto;
import com.healthner.healthner.repository.UserRepository;
import com.healthner.healthner.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {
}

    private final TrainerService trainerService;
    private final UserRepository userRepository;

    @GetMapping("user-mypage/new-trainer")
    public String trainer(Model model) {
        model.addAttribute("trainerForm", new TrainerDto.Form());
        return "new-trainer";
    }

    @PostMapping("user-mypage/new-trainer")
    public String save(@ModelAttribute("trainerForm") TrainerDto.Form form) {
        // session에 저장된 userInfo를 활용하여 user 정보를 조회할 예정
        User user = userRepository.findById(1L).orElse(null); // 임시로 DB에 저장된 user를 활용
        trainerService.save(form, user);

        return "redirect:/home";
    }
}