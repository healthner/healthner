package com.healthner.healthner.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class Remain extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "remain_id")
    private Long id;

    private Integer remainCount;

    private LocalDateTime remainPeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Remain(Integer remainCount, LocalDateTime remainPeriod, Product product, User user) {
        this.remainCount = remainCount;
        this.remainPeriod = remainPeriod;
        this.product = product;
        this.user = user;
    }

    public static Remain createRemain(Integer remainCount, LocalDateTime remainPeriod, Product product, User user) {
        return new Remain(remainCount, remainPeriod, product, user);
    }
}