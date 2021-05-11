package com.healthner.healthner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthner.healthner.controller.dto.GymDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class GymControllerTest {

    @Autowired private WebApplicationContext wac;

    @Autowired private GymRepository gymRepository;

    @Autowired private UserRepository userRepository;

    private MockMvc mvc;

    private MockHttpSession mockHttpSession;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = userRepository.findAll().get(0);         //추후 새롭게 User를 생성하는 것으로 변경
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userId", testUser.getId());

        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void register() throws Exception {
        GymDto.Request dto = new GymDto.Request();
        dto.setName("테스트짐");
        dto.setAddress("부천시");
        dto.setContent("테스트");
        dto.setBusinessNumber("031-123-123");

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/user-mypage/new-gym")
                    .session(mockHttpSession)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .content(objectMapper.writeValueAsString(dto))
                    .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        Gym result = gymRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("테스트짐"),
                () -> assertThat(result.getAddress()).isEqualTo("부천시"),
                () -> assertThat(result.getContent()).isEqualTo("테스트"),
                () -> assertThat(result.getBusinessNumber()).isEqualTo("031-123-123"),
                () -> assertThat(result.getCeo().getId()).isEqualTo(testUser.getId())
        ) ;


    }
}