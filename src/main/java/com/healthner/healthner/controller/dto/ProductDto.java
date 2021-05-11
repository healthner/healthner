package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Product;
import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.domain.Trainer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ProductDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        private Long gymId;
        private Long trainerId;
        private String name;
        private String content;
        private Integer price;
        private Integer count;
        private LocalDateTime period;
        private ProductType productType;

        public Product toEntity(Gym gym, Trainer trainer) {
            return Product.createProduct(gym, trainer, this.name, this.content,
                    this.price, this.count, this.period, this.productType);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Long gymId;
        private Long trainerId;
        private String name;
        private String content;
        private Integer price;
        private Integer count;
        private LocalDateTime period;
        private ProductType productType;

        public Response(Product product) {
            this.id = product.getId();
            this.gymId = product.getGym().getId();
            this.trainerId = product.getTrainer().getId();
            this.name = product.getName();
            this.content = product.getContent();
            this.price = product.getPrice();
            this.count = product.getCount();
            this.period = product.getPeriod();
            this.productType = product.getType();
        }
    }
}