package com.healthner.healthner.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckList extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "check_list_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CheckListStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private CheckList(CheckListStatus status, User user, Gym gym) {
        this.status = status;
        this.user = user;
        this.gym = gym;
    }

    public static CheckList createCheckList(CheckListStatus status, User user, Gym gym) {
        return new CheckList(status, user, gym);
    }
}