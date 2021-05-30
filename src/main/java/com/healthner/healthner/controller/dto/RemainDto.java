package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

public class RemainDto {
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @ToString
    public static class Request {
        private Integer remainCount;
        private LocalDateTime remainPeriod;
        private Long purchaseId;
        private Long userId;

        public Remain toEntity(User user, Purchase purchase) {
            return Remain.createRemain(this.getRemainCount(), this.getRemainPeriod(),
                    purchase, user);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Integer remainCount;
        private LocalDateTime remainPeriod;
        private String productName;
        private Long userId;

        public Response(Remain remain) {
            this.id = remain.getId();
            this.remainCount = remain.getRemainCount();
            this.remainPeriod = remain.getRemainPeriod();
            this.productName = remain.getPurchase().getProduct().getName();
            this.userId = remain.getUser().getId();
        }
    }
}
