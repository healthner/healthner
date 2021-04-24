package com.healthner.healthner.controller;

import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    @Auth(role = Role.USER)
    @GetMapping
    public String getReservation() {
        return "reservation";
    }
}
