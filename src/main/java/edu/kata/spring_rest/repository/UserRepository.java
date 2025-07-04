package edu.kata.spring_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import edu.kata.spring_rest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
