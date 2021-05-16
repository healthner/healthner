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
        private Long productId;
        private Long userId;

        public Remain toEntity(User user, Product product) {
            return Remain.createRemain(this.getRemainCount(), this.getRemainPeriod(),
                    product, user);
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
            this.productName = remain.getProduct().getName();
            this.userId = remain.getUser().getId();
        }
    }
}
