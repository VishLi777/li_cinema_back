package com.example.cinema.Controller;


import com.example.cinema.GraphQL.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GraphQLController {

    final GraphQLService graphQLService;

    public GraphQLController(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    // GRAPHQL
    @PostMapping("/getInfo")
    public ResponseEntity<Object> getInfo(@RequestBody String query){
        ExecutionResult executionResult = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }
}
