package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.CheckListDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.repository.TrainerRepository;
import com.healthner.healthner.repository.UserRepository;
import com.healthner.healthner.service.CheckListService;
import com.healthner.healthner.service.GymService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final CheckListService checkListService;

    @GetMapping
    public String index() {
        return "redirect:home";
    }

    @GetMapping("home")
    public String home(Model model, HttpSession session) {
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("trainerCount", trainerRepository.count());

        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        if (userInfo != null) {
            List<CheckListDto.Response> checkList = checkListService.findByUserId(userInfo.getId());
            model.addAttribute("checkList", checkList);
        }

        return "home";
    }
}