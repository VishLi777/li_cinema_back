package com.example.cinema.Controller;

import com.example.cinema.Service.MovieService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // REST_API
    @PostMapping("/addMovie")
    public void addMovie(@RequestBody String body) {
        movieService.addMovie(body);
    }

}