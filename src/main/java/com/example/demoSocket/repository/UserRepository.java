package com.example.demoSocket.repository;

import com.example.demoSocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username,String password);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
