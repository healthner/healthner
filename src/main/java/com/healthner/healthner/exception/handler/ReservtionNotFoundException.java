package com.healthner.healthner.exception.handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReservtionNotFoundException extends RuntimeException {

    private static final String MESSAGE = "예약내역이 존재하지 않습니다.";
    public ReservtionNotFoundException(){
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
