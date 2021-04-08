package com.healthner.healthner.controller.form;

import com.healthner.healthner.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class UserForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phoneNumber;

    public static User toUser(UserForm userForm) {
        return User.createUser(userForm.getEmail(), userForm.getPassword(),
                userForm.getName(), userForm.getPhoneNumber());
    }
}
