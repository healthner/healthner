package com.healthner.healthner.service;

import com.healthner.healthner.controller.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebAppConfiguration
@SpringBootTest
class GymServiceTest {

    @Autowired private GymService gymService;
    @Autowired private GymRepository gymRepository;
    @Autowired private UserRepository userRepository;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() throws Exception{                       // 임의로 로그인 환경 셋팅
        User user = userRepository.findAll().get(0);            // User 정보 가져오기

        session = new MockHttpSession();                        // 세션 생성
        session.setAttribute("User",user);                // 세션에 User 정보 등록

    }
    @AfterEach
    public void clear(){
        session.clearAttributes();
    }

    @Test
    void register() {

        GymDto.Request dto = new GymDto.Request();
        dto.setName("test");
        dto.setAddress("부천시");
        dto.setContent("헬스장");
        dto.setBusinessNumber("031-123-123");

        User ceo = (User)session.getAttribute("User");

        gymService.register(dto,ceo);

        Gym gym = gymRepository.findAll().get(1);

        assertThat(gym.getName()).isEqualTo(dto.getName());
        assertThat(gym.getAddress()).isEqualTo(dto.getAddress());
        assertThat(gym.getContent()).isEqualTo(dto.getContent());
        assertThat(gym.getBusinessNumber()).isEqualTo(dto.getBusinessNumber());
        assertThat(gym.getCeo().getId()).isEqualTo(ceo.getId());
    }
}