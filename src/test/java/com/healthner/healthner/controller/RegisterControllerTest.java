package com.healthner.healthner.controller;

import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.GymRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@WebAppConfiguration
@Rollback(value = false)
class RegisterControllerTest {

    private WebAppConfiguration webAppConfiguration;

    private GymRepository gymRepository;

    private MockMvc mvc;

    private MockHttpSession mockHttpSession;

    private User testUser;
    private Gym testGym;

    @BeforeEach
    public void initMvc(){

    }
}