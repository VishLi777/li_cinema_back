package com.example.cinema.Controller;

import com.example.cinema.GraphQL.GraphQLService;
import com.example.cinema.Service.CinemaService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;
    @Autowired
    GraphQLService graphQLService;

    @PostMapping("/addCinema")
    public void addCinema(@RequestBody String body){
        cinemaService.addCinema(body);
    }

    @PostMapping("/getAll")
    public ResponseEntity<Object> getAll(@RequestBody String query){
        ExecutionResult executionResult = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }



}
