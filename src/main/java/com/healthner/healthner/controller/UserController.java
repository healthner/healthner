package com.healthner.healthner.controller;

import com.healthner.healthner.controller.dto.UserDto;
import com.healthner.healthner.controller.form.UserForm;
import com.healthner.healthner.domain.User;
import com.healthner.healthner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users/new")
    public String signup(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "/example/createUserForm";
    }

    @PostMapping("/users/new")
    public String join(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/example/createUserForm";
        }

        User user = UserForm.toUser(userForm);
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> findUsers = userRepository.findAll();

        List<UserDto> collect = findUsers.stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());

        model.addAttribute("users", collect);

        return "/example/userList";
    }

    @GetMapping("/users/{userId}/edit")
    public String edit(@PathVariable(name = "userId") Long id, Model model) {
        User findUser = userRepository.findById(id).get();
        UserForm userForm = new UserForm();
        userForm.setEmail(findUser.getEmail());
        userForm.setPassword(findUser.getPassword());
        userForm.setName(findUser.getName());
        userForm.setPhoneNumber(findUser.getPhoneNumber());

        model.addAttribute("userForm", userForm);

        return "/example/updateUserForm";
    }

    @PostMapping("/users/{userId}/edit")
    public String edit(@PathVariable(name = "userId") Long id, UserForm userForm) {
        User findUser = userRepository.findById(id).get();
        User updateUser = UserForm.toUser(userForm);
        findUser.updateUser(updateUser);

        userRepository.save(findUser);

        return "redirect:/users";
    }
}