package com.healthner.healthner.controller;

import com.healthner.healthner.domain.Gym;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GymForm {

    private String name;
    private String address;
    private String content;
    private String businessNumber;

    public Gym getEntity(GymForm gymForm) {
        return Gym.createGym(gymForm.getName(), gymForm.getAddress(), gymForm.getContent(), getBusinessNumber(), null);
    }
}
