package edu.kata.spring_rest.service;

import org.springframework.stereotype.Service;

import java.util.List;

import edu.kata.spring_rest.model.Role;
import edu.kata.spring_rest.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRolesList() {
        return roleRepository.findAll();
    }
}
