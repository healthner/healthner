package com.healthner.healthner.repository;

import com.healthner.healthner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}