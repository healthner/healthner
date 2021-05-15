package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Auth(role = Role.USER)
    @GetMapping("{purchaseId}/new")
    public String getReservation(@PathVariable("purchaseId") Long purchaseId, Model model) {
        Boolean check = reservationService.isEmpty(purchaseId);
        if(!check){
            return "이미 예약된 상품입니다.";
        }
        else model.addAttribute("reservationDto", new ReservationDto.ReservRequest());
        return "reservation/create-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("{purchaseId}/new")
    public String postReservation(@ModelAttribute ReservationDto.ReservRequest reservRequest, @PathVariable("purchaseId") Long purchaseId) {
        Long userId = reservationService.put(reservRequest, purchaseId);
        return "redirect:/reservation/" + userId;
    }

    //마이페이지에서 예약 확인
    @Auth(role = Role.USER)
    @GetMapping("/{userId}")
    public String getMyEventList(@PathVariable("userId")Long userId, Model model) {
        List<ReservationDto.ReservResponse> reservations = reservationService.findByUserId(userId);
        model.addAttribute("reservations", reservations);
        return "user/my-page";
    }

    //마이페이지에서 예약 수정누르면 원래 예약값 세팅된 화면
    @Auth(role = Role.USER)
    @GetMapping("{reservId}/update")
    public String findModifyReservation(@PathVariable("reservId") Long reservId, Model model) {
        model.addAttribute("initial", reservationService.findById(reservId));
        return "reservation/modify-form";
    }

    //수정 진행, 저장
    @Auth(role = Role.USER)
    @PostMapping  ("/{reservId}/update")
    public String modify(@PathVariable("reservId") Long reservId, @ModelAttribute ReservationDto.ReservRequest request) {
        Long userId = reservationService.update(reservId, request);
        return "redirect:/reservation/" + userId;
    }

    //예약 삭제
    @Auth(role = Role.USER)
    @GetMapping("/{reservId}/delete")
    public String delete(@PathVariable("reservId") Long reservId) {
        Long userId = reservationService.delete(reservId);
        return "redirect:/reservation/" + userId;
    }

    //calendar에 나타낼 모든 예약
    @Auth(role = Role.USER)
    @GetMapping("/all")
    @ResponseBody
    public List<ReservationDto.ReservToCal>findAll() {
        return reservationService.findAll();
    }
}