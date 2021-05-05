package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
class GymServiceTest {

    @Autowired
    private GymService gymService;

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private UserRepository userRepository;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() throws Exception {
        User user = userRepository.findAll().get(0);

        session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

    }
    @AfterEach
    public void clear() {
        session.clearAttributes();
    }

    @Test
    void register() {
        GymDto.Request dto = new GymDto.Request();
        dto.setName("test");
        dto.setAddress("부천시");
        dto.setContent("헬스장");
        dto.setBusinessNumber("031-123-123");

        Long ceoId = (Long)session.getAttribute("userId");

        gymService.register(dto, ceoId);
    }
}