package com.healthner.healthner.repository;

import com.healthner.healthner.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    @DisplayName("trainer id 로 예약 목록을 조회하는 테스트")
    void findByTrainerId() {

        // given
        Long trainerId = 1L;

        // when
        List<Reservation> reservations = reservationRepository.findByTrainerId(trainerId);

        // then
        assertEquals(reservations.size(), 2);
    }
}