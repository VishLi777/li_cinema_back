package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.UserEntity;
import com.example.cinema.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserEntityService {

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    public void registration(String body) {
        UserEntity user = createUserFromJson(body);
        userRepository.save(user);
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "User created");
    }

    private UserEntity createUserFromJson(String body){
        String email = StaticMethods.parsingStringFromJson(body, "email");
        String password = StaticMethods.parsingStringFromJson(body, "password");
        String name = StaticMethods.parsingStringFromJson(body, "name");

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setName(name);
        user.setRoles(roleService.getRoleUSER());
        return user;
    }


}
