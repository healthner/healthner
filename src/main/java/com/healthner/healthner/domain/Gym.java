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
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gym extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gym_id")
    private Long id;

    private String name;

    private String address;

    private String content;

    private String businessNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ceo_id")
    private User ceo;

    private Gym(String name, String address, String content, String businessNumber, User ceo) {
        this.name = name;
        this.address = address;
        this.content = content;
        this.businessNumber = businessNumber;
        this.ceo = ceo;
    }

    public static Gym createGym(String name, String address, String content, String businessNumber, User ceo) {
        return new Gym(name, address, content, businessNumber, ceo);
    }

    public void updateGym(Gym gym) {
        this.name = gym.getName();
        this.address = gym.getAddress();
        this.content = gym.getContent();
        this.businessNumber = gym.getBusinessNumber();
    }
}