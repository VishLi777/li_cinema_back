package com.example.cinema.Controller;

import com.example.cinema.GraphQL.GraphQLService;
import com.example.cinema.Service.ReviewService;
import graphql.ExecutionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    final GraphQLService graphQLService;
    final ReviewService reviewService;

    public ReviewController(GraphQLService graphQLService, ReviewService reviewService) {
        this.graphQLService = graphQLService;
        this.reviewService = reviewService;
    }

    // REST_API
    @PostMapping("/addReview")
    public void addReview(@RequestBody String body) {
        reviewService.addReview(body);
    }

    // REST_API
    @PostMapping("/deleteReview")
    public void deleteReview(@RequestParam("id") Long id){
        reviewService.deleteReview(id);
    }

    // GRAPHQL
    @PostMapping("/getAll")
    public ResponseEntity<Object> getAll(@RequestBody String query){
        ExecutionResult executionResult = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }
}
