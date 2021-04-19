package com.healthner.healthner.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String userImageUrl;

    @Builder
    public User(String email, String password, String name, String phoneNumber, Provider provider, String userImageUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
    }

//    public static User createUser(String email, String password, String name, String phoneNumber, Provider provider) {
//        return new User(email, password, name, phoneNumber);
//    }
}