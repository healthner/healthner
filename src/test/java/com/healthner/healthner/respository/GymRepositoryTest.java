package com.healthner.healthner.respository;

import com.healthner.healthner.domain.Gym;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GymRepositoryTest {

    @Autowired GymRepository gymRepository;

    @Test
    @DisplayName("address 에 특정 단어를 선택하여 검색하는 테스트")
    void findByAddress() {

        // given
        String searchKeyword = "부천";

        // when
        List<Gym> result = gymRepository.findByAddressContaining(searchKeyword);

        // then
        for (Gym gym : result) {
            System.out.println(gym.getName());
        }
    }
}