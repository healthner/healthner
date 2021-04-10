package com.healthner.healthner.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    private String name;

    private String content;

    private Integer price;

    private Boolean deleteStatus;

    private Integer count;

    private LocalDateTime period;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private Product(Gym gym, Trainer trainer, String name, String content,
                    Integer price, Integer count, LocalDateTime period, ProductType type) {
        this.gym = gym;
        this.trainer = trainer;
        this.name = name;
        this.content = content;
        this.price = price;
        this.count = count;
        this.period = period;
        this.type = type;
        this.deleteStatus = true;
    }

    public static Product createProduct(Gym gym, Trainer trainer, String name, String content,
                                        Integer price, Integer count, LocalDateTime period, ProductType type) {
        return new Product(gym, trainer, name, content, price, count, period, type);
    }
}