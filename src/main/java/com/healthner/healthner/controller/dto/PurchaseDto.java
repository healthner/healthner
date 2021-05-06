package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Product;
import com.healthner.healthner.domain.Purchase;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

public class PurchaseDto {
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @ToString
    public static class Request {
        private Integer price;
        private Integer count;
        private LocalDateTime period;
        private Long userId;
        private Long gymId;
        private Long trainerId;
        private Long productId;

        public Purchase toEntity(User user, Gym gym, Trainer trainer, Product product) {
            return Purchase.creatPurchase(this.getPrice(), this.getCount(), this.getPeriod(),
                    user, gym, trainer, product);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Integer price;
        private Integer count;
        private LocalDateTime period;
        private Long userId;
        private String gymName;
        private String trainerName;
        private String productName;
        private String productType;

        public Response(Purchase purchase) {
            this.id = purchase.getId();
            this.price = purchase.getPrice();
            this.count = purchase.getCount();
            this.period = purchase.getPeriod();
            this.userId = purchase.getUser().getId();
            this.gymName = purchase.getGym().getName();
            this.trainerName = purchase.getTrainer().getUser().getName();
            this.productName = purchase.getProduct().getName();
            this.productType = purchase.getProduct().getType().toString();
        }
    }
}
