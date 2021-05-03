package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
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

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {

    private final GymService gymService;

    @GetMapping("/new")
    @Auth(role = Role.USER)
    public String register(Model model) {
        model.addAttribute("gym", new GymDto.Request());
        return "/gym/create-gym";
    }

    @PostMapping("/new")
    @Auth(role = Role.USER)
    public String register(@ModelAttribute("gym") GymDto.Request gymDto, HttpSession httpSession) {
        UserDto.Response user = (UserDto.Response) httpSession.getAttribute("userInfo");
        Long ceoId = user.getId();
        Long saveId = gymService.register(gymDto, ceoId);
        return "redirect:/gym/" + saveId + "/mypage";
    }

    @GetMapping("/{gymId}/update")
    @Auth(role = Role.USER)
    public String modify(@PathVariable Long gymId, Model model) {
        GymDto.Response gym = gymService.findById(gymId);
        model.addAttribute("gym", gym);
        return "/gym/create-gym";
    }

    @PostMapping("/{gymId}/update")
    @Auth(role = Role.USER)
    public String modify(@PathVariable Long gymId, @ModelAttribute("gym") GymDto.Request gymDto) {
        gymService.update(gymId, gymDto);
        return "redirect:/gym/" + gymId + "/mypage";
    }

    @GetMapping("search")
    public String getSearch() {
        return "gym/search";
    }

    @GetMapping("{gymId}/detail")
    public String detail(@PathVariable("gymId") Long id) {
        return "gym/detail";
    }

    @GetMapping("/{gymId}/mypage")
    @Auth(role = Role.USER)
    public String getGym(@PathVariable Long gymId, Model model) {
        GymDto.Response gym = gymService.findById(gymId);
        model.addAttribute("gym", gym);
        return "/gym/mypage";
    }

//    @GetMapping("{gymId}/my-page")
//    public String myPage(@PathVariable("gymId") Long id) {
//        return "gym/mypage";
//    }
}
