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

    @GetMapping(value = "/test2")
    public String test2(){
        return "testcalendar";
    }

    @GetMapping(value = "/test1")
    public String test1(){
        return "selectable";}



//    @GetMapping(value = "/selectable/{reservation_id}/update")
//    public String updateReservation(@PathVariable(name = "reservation_id") Long id,ReservationDto reservationDto) {
//        reservationSerivce.modify(id,reservationDto);
//        return "redirect:selectable";
//    }




//    @PutMapping("/{id}")
  //  public void modifyReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
   //     reservationSerivce.modify(id, reservation);
    //}
    //@DeleteMapping("/{id}")
    //public void deleteMapping(@PathVariable Long id) {
      //  reservationSerivce.delete(id);
    //}
}
