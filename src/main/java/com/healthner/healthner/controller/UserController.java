package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.PurchaseService;
import com.healthner.healthner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PurchaseService purchaseService;

    //유저의 구매 내역 리스트
    @Auth(role = Role.USER)
    @GetMapping("/purchase")
    public String findMyPurchaseList(HttpSession httpSession, Model model){
        UserDto.Response response = (UserDto.Response) httpSession.getAttribute("userInfo");

        if(response == null){
            return "loginerror";
        }else {
            Long userId = response.getId();
            model.addAttribute("purchaseList", purchaseService.findByUserId(userId));
        }
        return "purchase/purchase";
    }
}
