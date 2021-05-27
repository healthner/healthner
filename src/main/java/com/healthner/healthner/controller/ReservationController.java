package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.controller.model.Message;
import com.healthner.healthner.interceptor.Auth;
import com.healthner.healthner.interceptor.Role;
import com.healthner.healthner.service.RemainService;
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
    private final RemainService remainService;

    @Auth(role = Role.USER)
    @GetMapping("{purchaseId}/new")
    public String getReservation(@PathVariable("purchaseId") Long purchaseId, Model model) {
        model.addAttribute("reservationDto", new ReservationDto.ReservRequest());
        return "reservation/create-form";
    }

    @Auth(role = Role.USER)
    @PostMapping("{purchaseId}/new")
    public String postReservation(@ModelAttribute ReservationDto.ReservRequest reservRequest, @PathVariable("purchaseId") Long purchaseId, Model model) {
        if (reservationService.put(reservRequest, purchaseId)) {
            remainService.minusCount(remainService.findByPurchaseId(purchaseId));
        } else {
            model.addAttribute("data", new Message("예약이 불가능한 시간입니다.", "/user/my-page"));
            return "common/message";
        }
        return "redirect:/user/my-page";
    }

    //마이페이지에서 예약 수정누르면 원래 예약값 세팅된 화면
    @Auth(role = Role.USER)
    @GetMapping("{reserveId}/update")
    public String findModifyReservation(@PathVariable("reserveId") Long reserveId, Model model) {
        model.addAttribute("reservationDto", reservationService.findById(reserveId));
        return "reservation/create-form";
    }

    //수정 진행, 저장
    @Auth(role = Role.USER)
    @PostMapping("/{reserveId}/update")
    public String modify(@PathVariable("reserveId") Long reservId, @ModelAttribute ReservationDto.ReservRequest request) {
        Long userId = reservationService.update(reservId, request);
        return "redirect:/user/my-page";
    }

    //예약 삭제
    @Auth(role = Role.USER)
    @GetMapping("/{reserveId}/delete")
    public String delete(@PathVariable("reserveId") Long reserveId) {
        remainService.plusCount(remainService.findByPurchaseId(reservationService.findPurchaseId(reserveId)));
        reservationService.delete(reserveId);
        return "redirect:/user/my-page";
    }

    //calendar에 나타낼 모든 예약
    @Auth(role = Role.USER)
    @GetMapping("/all")
    @ResponseBody
    public List<ReservationDto.ReservToCal> findAll() {
        return reservationService.findAll();
    }
}