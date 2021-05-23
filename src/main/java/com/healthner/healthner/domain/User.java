package com.healthner.healthner.domain;

import com.healthner.healthner.interceptor.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String userImageUrl;

    @Enumerated(EnumType.STRING)
    Role role;

    @Builder
    public User(String email, String password, String name, String phoneNumber, Provider provider, String userImageUrl, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
        this.role = role;
    }

    public void updateUser(User updateUser) {
        this.email = updateUser.getEmail();
        this.password = updateUser.getPassword();
        this.name = updateUser.getName();
        this.userImageUrl = updateUser.getUserImageUrl();
        this.provider = updateUser.getProvider();
        this.role = updateUser.role;
    }

    public void inputPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}