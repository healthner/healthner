package com.healthner.healthner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
class RegisterControllerTest {

    @Autowired private WebApplicationContext webApplicationContext;

    @Autowired private GymRepository gymRepository;

    @Autowired private UserRepository userRepository;

    private MockMvc mvc;

    private MockHttpSession mockHttpSession;

    private User testUser;

    @BeforeEach
    public void setUp(){
        testUser = userRepository.findAll().get(0);
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("User",testUser);

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void register() throws Exception{
        GymDto.Request dto = new GymDto.Request();
        dto.setName("테스트짐");
        dto.setAddress("부천시");
        dto.setContent("테스트");
        dto.setBusinessNumber("031-123-123");

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/user-mypage/new-gym")
                    .session(mockHttpSession)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto))
                    .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());

        Gym gym = gymRepository.findAll().get(1);

        assertThat(gym.getName()).isEqualTo(dto.getName());
        assertThat(gym.getAddress()).isEqualTo(dto.getAddress());
        assertThat(gym.getContent()).isEqualTo(dto.getContent());
        assertThat(gym.getBusinessNumber()).isEqualTo(dto.getBusinessNumber());
        assertThat(gym.getCeo().getId()).isEqualTo(testUser.getId());    }
}