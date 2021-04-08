
package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Purchase;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor(staticName = "of")
public class ReservationDto {
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User user ;
    private Trainer trainer;
    private Purchase purchase;

    public Reservation getEntity(ReservationDto reservationDto){
        return Reservation.createReservation(reservationDto.getDate(),reservationDto.getStartTime(),
                 reservationDto.getEndTime(),reservationDto.getUser(),reservationDto.getTrainer(),
                reservationDto.getPurchase());
    }


}
