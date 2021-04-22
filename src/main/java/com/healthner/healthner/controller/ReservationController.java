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
    private final ReservationSerivce reservationSerivce;
    private final ReservationRepository reservationRepository;

    @Auth(role = Role.USER)
    @GetMapping
    public String getReservation() {
        return "reservation";
    }

    @PostMapping(value = "/reservation/new")
    @ResponseStatus(HttpStatus.CREATED)  //성공일 경우 201번
    public String postReservation(@ModelAttribute ReservationDto.ReservRequest reservRequest) {
        reservationSerivce.put(reservRequest);
        return "redirect:reservation";
    }


    //마이페이지에서 예약 확인
    @GetMapping(value = "/reservation/{userId}")
    public String getMyEventList(@PathVariable("userId")Long userId,Model model) {
        List<ReservationDto.ReservRequest> reservations = reservationSerivce.getMyEventList(userId);
        model.addAttribute("reservations",reservations);
        return "user-mypage";
    }


    //마이페이지에서 예약 수정누르면 원래 예약값 세팅된 화면
    @GetMapping("/reservation/{reservId}/modify")
    public String findModifyReservation(@PathVariable("reservId") Long reservId, Model model) {
        model.addAttribute("initial",reservationSerivce.findModifyReservation(reservId));
        return "modifyReservForm";
    }

    //수정 진행, 저장
    @PostMapping  ("/reservation/{reservId}/modify")
    public String modify(@PathVariable(name = "reservId") Long reservId, ReservationDto.ReservRequest request) {
        reservationSerivce.modify(reservId, request);
        return "redirect:/user-mypage";
    }

    //예약 삭제
    @GetMapping("/reservation/{reservId}/delete")
    public String delete(@PathVariable(name = "reservId") Long reservId) {
      reservationSerivce.delete(reservId);
        return "redirect:/user-mypage";
    }


    //calendar에 나타낼 모든 예약
    @RequestMapping(value="/reservation/all", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<ReservationDto.ReservResponse>reservationALl(ReservationDto.ReservRequest reservRequest) {
        return reservationSerivce.eventToCalenar(reservRequest);
    }

}
