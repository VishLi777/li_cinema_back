package com.example.cinema.Controller;

import com.example.cinema.Service.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    final MovieService movieService;

    // REST_API
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // REST_API
    @PostMapping("/addMovie")
    public void addMovie(@RequestBody String body) {
        movieService.addMovie(body);
    }

        // REST_API
    @PostMapping("/deleteMovie")
    public void deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
    }

}