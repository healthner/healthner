package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.PurchaseService;
import com.healthner.healthner.service.RemainService;
import com.healthner.healthner.service.ReservationService;
import com.healthner.healthner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final ReservationService reservationService;
    private final RemainService remainService;

    //유저의 구매 내역 리스트
    @Auth(role = Role.USER)
    @GetMapping("/purchase")
    public String findMyPurchaseList(HttpSession httpSession, Model model) {
        UserDto.Response response = (UserDto.Response) httpSession.getAttribute("userInfo");

        if (response == null) {
            return "loginerror";
        } else {
            Long userId = response.getId();
            model.addAttribute("PT", purchaseService.findByUserIdAndPT(userId));
            model.addAttribute("Normal", purchaseService.findByUserIdAndNormal(userId));

        }
        return "purchase/purchase";
    }

    @Auth(role = Role.USER)
    @GetMapping("my-page")
    public String myPage(HttpSession session, Model model) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");

        List<ReservationDto.ReservResponse> reservations = reservationService.findByUserId(userInfo.getId());
        model.addAttribute("reservations", reservations);

        Long userId = userInfo.getId();
        model.addAttribute("PT", purchaseService.findByUserIdAndPT(userId));
        model.addAttribute("Normal", purchaseService.findByUserIdAndNormal(userId));
        model.addAttribute("endPts", purchaseService.endPt(userId));
        model.addAttribute("endNormals", purchaseService.endNormal(userId));
        model.addAttribute("remainList", remainService.findByUserId(userId));

        return "user/my-page";
    }

    @Auth(role = Role.USER)
    @GetMapping("/number")
    public String inputNumber(Model model) {
        String phoneNumber = new String();
        model.addAttribute("phoneNumber", phoneNumber);
        return "/number-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("/number")
    public String inputNumber(HttpSession httpSession, @ModelAttribute("phoneNumber") String phone) {
        UserDto.Response response = (UserDto.Response) httpSession.getAttribute("userInfo");
        userService.InputPhone(response.getId(), phone);
        return "redirect:/home";

    }
}
