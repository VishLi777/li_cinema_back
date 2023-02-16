package com.example.cinema.Controller;

import com.example.cinema.Service.HallService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hall")
public class HallController {

    @Autowired
    HallService hallService;

    // REST_API
    @PostMapping("/addHall")
    public void addHall(@RequestBody String body){
        hallService.addHall(body);
    }

}
