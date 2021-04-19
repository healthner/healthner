package com.healthner.healthner.service;

import com.healthner.healthner.domain.User;
import com.healthner.healthner.kakaologin.dto.UserDto;
import com.healthner.healthner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 회원가입
    @Transactional
    public Long join(User user){
        userRepository.save(user);
        return user.getId();
    }

    // 유저 조회
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public UserDto.UserInfo findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null ? new UserDto.UserInfo(user) : null;
    }
}
