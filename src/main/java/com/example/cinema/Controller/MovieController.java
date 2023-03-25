package com.example.cinema.Controller;

import com.example.cinema.GraphQL.GraphQLService;
import com.example.cinema.Service.MovieService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    final GraphQLService graphQLService;
    final MovieService movieService;

    // REST_API
    public MovieController(MovieService movieService, GraphQLService graphQLService) {
        this.movieService = movieService;
        this.graphQLService = graphQLService;
    }

    // REST_API
    @PostMapping("/addMovie")
    public void addMovie(@RequestBody String body) {
        movieService.addMovie(body);
    }

    // REST_API
    @DeleteMapping("/deleteMovie")
    public void deleteMovie(@RequestParam("id") Long id){
        movieService.deleteMovie(id);
    }

    // GRAPHQL
    @PostMapping("/getAll")
    public ResponseEntity<Object> getAll(@RequestBody String query){
        ExecutionResult executionResult = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

}
