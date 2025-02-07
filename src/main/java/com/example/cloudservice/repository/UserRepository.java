package com.example.cloudservice.repository;

import com.example.cloudservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String login);

    void removeByLogin(String login);

}
