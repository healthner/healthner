package com.healthner.healthner.controller;


import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationSerivce;

    @Auth(role = Role.USER)
    @GetMapping
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservation/new")
    //@RequestMapping(value = "/{id}")
    public String getReservation(Model model) {

        model.addAttribute("reservationDto",new ReservationDto.ReservRequest());
        return "create-reserv-form";
    }

    @PostMapping("/reservation/new")
    @ResponseStatus(HttpStatus.CREATED)  //성공일 경우 201번
    public String postReservation(@ModelAttribute ReservationDto.ReservRequest reservRequest) {
        reservationSerivce.put(reservRequest);
        return "redirect:reservation";
    }

    //마이페이지에서 예약 확인
    @GetMapping("/reservation/{userId}")
    public String getMyEventList(@PathVariable("userId")Long userId, Model model) {
        List<ReservationDto.ReservResponse> reservations = reservationSerivce.getMyEventList(userId);
        model.addAttribute("reservations",reservations);
        return "user-mypage";
    }

    //마이페이지에서 예약 수정누르면 원래 예약값 세팅된 화면
    @GetMapping("/reservation/{reservId}/modify")
    public String findModifyReservation(@PathVariable("reservId") Long reservId, Model model) {
        model.addAttribute("initial",reservationSerivce.findModifyReservation(reservId));
        return "modify-reserv-form";
    }

    //수정 진행, 저장
    @PostMapping  ("/reservation/{reservId}/modify")
    public String modify(@PathVariable("reservId") Long reservId, ReservationDto.ReservRequest request) {
        reservationSerivce.modify(reservId, request);
        return "redirect:/user-mypage";
    }

    //예약 삭제
    @GetMapping("/reservation/{reservId}/delete")
    public String delete(@PathVariable("reservId") Long reservId) {
        reservationSerivce.delete(reservId);
        return "redirect:/user-mypage";
    }

    //calendar에 나타낼 모든 예약
    @GetMapping("/reservation/all")
    @ResponseBody
    public List<ReservationDto.ReservToCal>findAll() {
        return reservationSerivce.findAll();
    }
}