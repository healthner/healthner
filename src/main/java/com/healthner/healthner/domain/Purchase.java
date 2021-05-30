package com.healthner.healthner.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    private Integer price;

    private Integer count;

    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm")
    private LocalDateTime period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Purchase(Integer price, Integer count, LocalDateTime period,
                     User user, Gym gym, Trainer trainer, Product product) {
        this.price = price;
        this.count = count;
        this.period = period;
        this.user = user;
        this.gym = gym;
        this.trainer = trainer;
        this.product = product;
    }

    public static Purchase createPurchase(Integer price, Integer count, LocalDateTime period,
                                          User user, Gym gym, Trainer trainer, Product product) {
        return new Purchase(price, count, period, user, gym, trainer, product);
    }
}