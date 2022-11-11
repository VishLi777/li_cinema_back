package com.example.cinema.Controller;

import com.example.cinema.Service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserEntityController {

    @Autowired
    UserEntityService userEntityService;

    @PostMapping("/registration")
    public void registration(@RequestBody String body){
        userEntityService.registration(body);
    }

}
