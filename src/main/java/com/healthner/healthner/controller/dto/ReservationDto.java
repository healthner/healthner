
package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Purchase;
import com.healthner.healthner.domain.Reservation;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString
public class ReservationDto {
    private String title;
    private Long id;

    private String date;
    private String startTime;
    private String endTime;
    @Nullable
    private User user ;
    @Nullable
    private Trainer trainer;
    @Nullable
    private Purchase purchase;

    public Reservation getEntity(ReservationDto reservationDto){
        return Reservation.createReservation(reservationDto.getDate(),reservationDto.getStartTime(),
                 reservationDto.getEndTime(),reservationDto.getUser(),reservationDto.getTrainer(),
                reservationDto.getPurchase());
    }


    //예약목록에서 dto와 비교하여 client에게 제공
    public ReservationDto(Reservation reservation){
        this.id = reservation.getId();
        this.date = reservation.getDate();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();

    }


}
