package com.healthner.healthner.service;

import com.healthner.healthner.controller.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.security.RunAs;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class GymServiceTest {
    @Autowired
    private GymService gymService;
    @Autowired
    private GymRepository gymRepository;
    @Autowired
    private UserRepository userRepository;

    private MockHttpSession session;
//    private MockHttpServletRequest request;

    @BeforeEach
    public void setUp() throws Exception{                       // 임의로 로그인 환경 셋팅
        User user = userRepository.findAll().get(0);            // User 정보 가져오기

        session = new MockHttpSession();                        // 세션 생성
        session.setAttribute("User",user);                // 세션에 User 정보 등록

//        request = new MockHttpServletRequest();
//        request.setSession(session);
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }
    @AfterEach
    public void clear(){
        session.clearAttributes();
    }

    @Test
    void register() {

        GymDto.Request dto = new GymDto.Request("test","test",
                                        "test","123-123");
        User currentUser = (User)session.getAttribute("User"); // 현재 로그인한 User
        gymRepository.save(dto.toEntity(dto,currentUser));          // Gym 생성

        Gym gym = gymRepository.findAll().get(1);          // 등록된 Gym 불러오기

        assertThat(gym.getName()).isEqualTo(dto.getName());// Gym 문제 없이 생성 되었는지 비교
        assertThat(gym.getAddress()).isEqualTo(dto.getAddress());
        assertThat(gym.getContent()).isEqualTo(dto.getContent());
        assertThat(gym.getBusinessNumber()).isEqualTo(dto.getBusinessNumber());
        assertThat(gym.getCeo().getId()).isEqualTo(currentUser.getId());// 현재 로그인 User와 Gym에 등록된 User 비교
    }
}