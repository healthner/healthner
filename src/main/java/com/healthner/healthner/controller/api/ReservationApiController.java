package com.healthner.healthner.controller.api;

import com.healthner.healthner.controller.dto.ReservationDto;
import com.healthner.healthner.controller.dto.TrainerDto;
import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.service.PurchaseService;
import com.healthner.healthner.service.ReservationService;
import com.healthner.healthner.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;
    private final TrainerService trainerService;
    private final PurchaseService purchaseService;

    @GetMapping("/reservation/trainer/calendar")
    public List<ReservationDto.ResponseToTrainer> getTrainerReserve(HttpSession session) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        TrainerDto.Form findForm = trainerService.findByUserId(userInfo.getId());
        return reservationService.findByTrainerId(findForm.getId());
    }

    @GetMapping("/reservation/user/calendar")
    public List<ReservationDto.ResponseToUser> getUserReserve(HttpSession session) {
        UserDto.Response userInfo = (UserDto.Response) session.getAttribute("userInfo");
        return reservationService.findByUserId(userInfo.getId());

    }

    //예약시 해당 트레이너의 캘린더로 스케줄 확인 가능
    @GetMapping("/reservation/{purchaseId}")
    public List<ReservationDto.ResponseToTrainer> getTrainerReserve2(@PathVariable("purchaseId") Long purchaseId) {
        return reservationService.findByTrainerId(purchaseService.findTrainer(purchaseId));
    }


}