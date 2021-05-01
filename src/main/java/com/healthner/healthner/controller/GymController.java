package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.kakaologin.dto.UserDto;
import com.healthner.healthner.service.GymService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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
    @Auth(role = Role.USER)
    public String register(@ModelAttribute("gym") GymDto.Request gymDto, HttpSession httpSession) {
        UserDto.UserInfo userInfo = (UserDto.UserInfo) httpSession.getAttribute("userInfo");
        Long ceoId = userInfo.getId();
        Long saveId = gymService.register(gymDto, ceoId);
        return "/gym-mypage";
    }

    @GetMapping("/new-gym/{gymId}")
    @Auth(role = Role.USER)
    public String modify(@PathVariable Long gymId, Model model) {
        GymDto.Response gym = gymService.findById(gymId);
        model.addAttribute("gym", gym);
        return "/new-gym"+gymId;
    }

    @PostMapping("/new-gym/{gymId}")
    @Auth(role = Role.USER)
    public String modify(@PathVariable Long gymId, @ModelAttribute("gym") GymDto.Request gymDto) {
        gymService.update(gymId, gymDto);
        return "/new-gym"+gymId;
    }

    @GetMapping("search")
    public String getSearch() {
        return "gym/search";
    }

    @GetMapping("{gymId}/detail")
    public String detail(@PathVariable("gymId") Long id) {
        return "gym/detail";
    @GetMapping("/gym-mypage")
    @Auth(role = Role.USER)
    public String getGym(Model model, Long gymId) {
         GymDto.Response gym = gymService.findById(gymId);
         model.addAttribute("content", gym.getContent());
        return "/gym-mypage";
    }

    @GetMapping("{gymId}/my-page")
    public String myPage(@PathVariable("gymId") Long id) {
        return "gym/mypage";
    }
}
