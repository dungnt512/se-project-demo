package com.example.projectdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.projectdemo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

