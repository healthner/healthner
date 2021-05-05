package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.controller.model.Message;
import com.healthner.healthner.dto.TrainerDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;

    @Auth(role = Role.USER)
    @GetMapping("new")
    public String getTrainer(Model model) {
        model.addAttribute("trainerForm", new TrainerDto.Form());
        return "trainer/trainer-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("new")
    public String save(@ModelAttribute("trainerForm") TrainerDto.Form form, HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        TrainerDto.Form findForm = trainerService.findByUserId(userInfo.getId());
        if (findForm != null) {
            model.addAttribute("data", new Message("이미 트레이너를 등록하였습니다.", "/home"));
            return "common/message";
        }

        try {
            trainerService.save(form, userInfo.getId());
        } catch (IllegalArgumentException e) {
            model.addAttribute("data", new Message("트레이너 등록에 실패하였습니다.", "/home"));
            return "common/message";
        }

        model.addAttribute("data", new Message("트레이너에 등록되었습니다.", "/home"));
        return "common/message";
    }

    @Auth(role = Role.USER)
    @GetMapping("update")
    public String getUpdate(Model model, HttpSession session) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        TrainerDto.Form trainerForm = trainerService.findByUserId(userInfo.getId());
        model.addAttribute("trainerForm", trainerForm);
        return "trainer/trainer-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("update")
    public String update(@ModelAttribute("trainerForm") TrainerDto.Form form,
                         HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        TrainerDto.Form trainerForm = trainerService.findByUserId(userInfo.getId());

        try {
            trainerService.update(trainerForm.getId(), userInfo.getId(), form);
        } catch (IllegalArgumentException e) {
            model.addAttribute("data",
                    new Message("존재하지 않는 트레이너로 인하여 수정을 실패하였습니다.", "/home"));
            return "common/message";
        }

        model.addAttribute("data", new Message("수정이 완료되었습니다.", "/home"));
        return "common/message";
    }
}