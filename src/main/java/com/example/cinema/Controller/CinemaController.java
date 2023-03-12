package com.example.cinema.Controller;

import com.example.cinema.GraphQL.GraphQLService;
import com.example.cinema.Service.CinemaService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController {

    final CinemaService cinemaService;
    final GraphQLService graphQLService;

    @Autowired
    public CinemaController(CinemaService cinemaService, GraphQLService graphQLService) {
        this.cinemaService = cinemaService;
        this.graphQLService = graphQLService;
    }

    // REST_API
    @PostMapping("/addCinema")
    public void addCinema(@RequestBody String body) {
        cinemaService.addCinema(body);
    }


    // GRAPHQL
    @PostMapping("/getAll")
    public ResponseEntity<Object> getAll(@RequestBody String query){
        ExecutionResult executionResult = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

    // ?
//    @DeleteMapping("/delete")


    // ?
//    @PutMapping("/edit")

    @PostMapping("/deleteCinema")
    public void deleteCinema(@RequestParam("id") Long id){
        cinemaService.deleteCinema(id);
    }


}
