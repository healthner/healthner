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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class PurchaseDto {
    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @ToString
    public static class Request {
        private Integer price;
        private Integer count;

        @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm")
        private LocalDateTime period;
        private Long userId;
        private Long gymId;
        private Long trainerId;
        private Long productId;

        public Purchase toEntity(User user, Gym gym, Trainer trainer, Product product) {
            return Purchase.createPurchase(product.getPrice(), this.getCount(), this.getPeriod(),
                    user, gym, trainer, product);
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class ResponsePT {
        private Long id;
        private Integer price;
        private Integer count;
        private Long userId;
        private Long gymId;
        private String gymName;
        private String trainerName;
        private String productName;
        private String productType;

        public ResponsePT(Purchase purchase) {
            this.id = purchase.getId();
            this.price = purchase.getPrice();
            this.count = purchase.getCount();
            this.userId = purchase.getUser().getId();
            this.gymId = purchase.getGym().getId();
            this.gymName = purchase.getGym().getName();
            this.trainerName = purchase.getTrainer().getUser().getName();
            this.productName = purchase.getProduct().getName();
            this.productType = purchase.getProduct().getType().toString();
        }
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class ResponseNormal {
        private Long id;
        private Integer price;
        private LocalDateTime period;
        private String periodToString;
        private Long userId;
        private Long gymId;
        private String gymName;
        private String productName;
        private String productType;

        public ResponseNormal(Purchase purchase) {
            this.id = purchase.getId();
            this.price = purchase.getPrice();
            this.period = purchase.getPeriod();
            this.periodToString = purchase.getPeriod().toString().replace("T","   ").substring(0,18);
            this.userId = purchase.getUser().getId();
            this.gymId = purchase.getGym().getId();
            this.gymName = purchase.getGym().getName();
            this.productName = purchase.getProduct().getName();
            this.productType = purchase.getProduct().getType().toString();
        }
    }
}

