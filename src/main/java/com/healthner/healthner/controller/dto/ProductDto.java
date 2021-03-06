package com.healthner.healthner.controller.dto;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Product;
import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.domain.Trainer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class ProductDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Request {
        private Long gymId;
        private Long trainerId;
        private String name;
        private String content;
        private Integer price;
        private Integer count;
        private Integer period;
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
        private GymDto.Response gym;
        private TrainerDto.Response trainer;
        private String name;
        private String content;
        private Integer price;
        private Integer count;
        private Integer period;
        private Boolean deleteStatus;
        private ProductType productType;

        public Response(Product product) {
            this.id = product.getId();
            this.gym = new GymDto.Response(product.getGym());
            if (product.getTrainer() != null) {
                this.trainer = new TrainerDto.Response(product.getTrainer());
            }
            this.name = product.getName();
            this.content = product.getContent();
            this.price = product.getPrice();
            this.count = product.getCount();
            this.period = product.getPeriod();
            this.deleteStatus = product.getDeleteStatus();
            this.productType = product.getType();
        }

        public ProductDto.Request toRequest() {
            return new ProductDto.Request(
                    this.gym.getId(),
                    this.trainer.getId(),
                    this.name,
                    this.content,
                    this.price,
                    this.count,
                    this.period,
                    this.productType
            );
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResponseNormal {
        private Long id;
        private Long gymId;
        private String name;
        private String content;
        private Integer price;
        private Integer count;
        private Integer period;
        private ProductType productType;
        private Boolean deleteStatus;

        public ResponseNormal(Product product) {
            this.id = product.getId();
            this.gymId = product.getGym().getId();
            this.name = product.getName();
            this.content = product.getContent();
            this.price = product.getPrice();
            this.count = product.getCount();
            this.period = product.getPeriod();
            this.productType = product.getType();
            this.deleteStatus = product.getDeleteStatus();
        }
    }
}