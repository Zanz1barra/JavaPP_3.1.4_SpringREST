package edu.kata.spring_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.kata.spring_rest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
