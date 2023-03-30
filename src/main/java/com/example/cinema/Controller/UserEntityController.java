package com.example.cinema.Controller;

import com.example.cinema.Service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserEntityController {

    @Autowired
    UserEntityService userService;

    @PostMapping("/registration")
    public void registration(@RequestBody String body){
        userService.registration(body);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam("id") Long userId){
        userService.deleteUser(userId);
    }

}
