package com.healthner.healthner.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Trainer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 자기 자신

    private String career;

    private Trainer(Gym gym, User user, String career) {
        this.gym = gym;
        this.user = user;
        this.career = career;
    }

    public static Trainer createTrainer(Gym gym, User user, String career) {
        return new Trainer(gym, user, career);
    }

    public void updateTrainer(Trainer trainer) {
        this.gym = trainer.getGym();
        this.user = trainer.getUser();
        this.career = trainer.getCareer();
    }
}