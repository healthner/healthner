package com.healthner.healthner.service;

import com.healthner.healthner.controller.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GymServiceTest {

    @InjectMocks
    private GymService gymService;

    @Mock
    private GymRepository gymRepository;

    @Autowired
    private UserRepository userRepository;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() throws Exception {
        User user = userRepository.findAll().get(0);

        session = new MockHttpSession();
        session.setAttribute("User", user);

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

        User currentUser = (User)session.getAttribute("User");

        gymRepository.save(dto.toEntity(dto, currentUser));

        verify(gymRepository, times(1)).save(any(Gym.class));

    }
}