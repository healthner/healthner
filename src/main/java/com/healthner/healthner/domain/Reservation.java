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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    private Reservation(LocalDate date, LocalDateTime startTime, LocalDateTime endTime,
                        User user, Trainer trainer, Purchase purchase) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.trainer = trainer;
        this.purchase = purchase;
    }

    public static Reservation createReservation(LocalDate date, LocalDateTime startTime, LocalDateTime endTime,
                                                User user, Trainer trainer, Purchase purchase) {
        return new Reservation(date, startTime, endTime, user, trainer, purchase);
    }

    public void updateReservation(Reservation reservation){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.trainer = trainer;
        this.purchase = purchase;
    }
}