package com.example.cinema.Controller;

import com.example.cinema.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @PostMapping("/addCinema")
    public void addCinema(@RequestBody String body){
        cinemaService.addCinema(body);
    }

}
