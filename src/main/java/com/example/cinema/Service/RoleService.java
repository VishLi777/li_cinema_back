package com.example.cinema.Service;

import com.example.cinema.Entity.ERoles;
import com.example.cinema.Entity.Role;
import com.example.cinema.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getRoleUSER(){
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.getById(2L);
        role.setRole(ERoles.USER);
        roles.add(role);
        return roles;
    }

}
