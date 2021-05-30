package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Purchase;
import com.healthner.healthner.domain.Remain;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class RemainDto {
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @ToString
    public static class Request {
        private Integer remainCount;
        private Long purchaseId;
        private Long userId;

        public Remain toEntity(User user, Purchase purchase) {
            return Remain.createRemain(this.getRemainCount(),
                    purchase, user);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Integer remainCount;
        private String productName;
        private Long userId;

        public Response(Remain remain) {
            this.id = remain.getId();
            this.remainCount = remain.getRemainCount();
            this.productName = remain.getPurchase().getProduct().getName();
            this.userId = remain.getUser().getId();
        }
    }
}
