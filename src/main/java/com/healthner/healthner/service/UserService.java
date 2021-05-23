package com.healthner.healthner.service;

import com.healthner.healthner.domain.User;
import com.healthner.healthner.controller.dto.UserDto;
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
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Long update(Long id, User updateUser) {
        User findUser = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 user id 입니다. id=" + id)
        );

        findUser.updateUser(updateUser);
        return findUser.getId();
    }

    // 유저 조회
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 user id 입니다. id=" + id)
        );
    }

    public UserDto.Response findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null ? new UserDto.Response(user) : null;
    }

    @Transactional
    public void InputPhone(Long id, String phone) {
        User findUser = findById(id);
        findUser.inputPhone(phone);
    }
}
