
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

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ReservationDto {

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @ToString
    public static class ReservRequest {
        private String title;
        private Long id;

        private Date date;
        private String startTime;
        private String endTime;
        @Nullable
        private User user;
        @Nullable
        private Trainer trainer;
        @Nullable
        private Purchase purchase;


        //예약을 dto로 받고 이를 ㄹㅇ reservation 객체로 생성해줌
        public Reservation getEntity(ReservationDto.ReservRequest reservRequest) {
            return Reservation.createReservation(reservRequest.getDate(), reservRequest.getStartTime(),
                    reservRequest.getEndTime(), reservRequest.getUser(), reservRequest.getTrainer(),
                    reservRequest.getPurchase());
        }


        //예약목록에서 dto와 비교하여 client에게 제공
        public ReservRequest(Reservation reservation) {
            this.id = reservation.getId();
            this.date = reservation.getDate();
            this.startTime = reservation.getStartTime();
            this.endTime = reservation.getEndTime();

        }

    }


    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class ReservResponse {
        private String title;
        private Date date; //  calendar 상에서 날짜를 의미 :start

        //예약을 가공해서 calendar에 뿌려줄 수 있게끔
        public ReservResponse(ReservRequest reservRequest){
            this.title = reservRequest.getTitle();
            this.date = reservRequest.getDate();
        }




    }


}




  //[ {title: 'All Day Event', start: '2015-02-01'},
    //
    //  {title: 'Long Event', start: '2015-02-07', end: '2015-02-10' } ]


    // 클->서버 예약을 등록  [{"title":null,"id":1,"date":"2021-04-09",
    // "startTime":"11","endTime":"16","user":null,"trainer":null,"purchase":

    //response
    // 서버 ->클  예약을 보여줘야됨 아래와 같이 해야 calendar 적용됨
    // [ {title: 'All Day Event', start: '2015-02-01'}




