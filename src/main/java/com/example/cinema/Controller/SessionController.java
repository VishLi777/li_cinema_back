package com.example.cinema.Controller;


import com.example.cinema.GraphQL.GraphQLService;
import com.example.cinema.Service.SessionService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    @Autowired
    final SessionService sessionService;

    @Autowired
    GraphQLService graphQLService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // REST_API
    @PostMapping("/addSession")
    public void addSession(@RequestBody String body) {
        sessionService.addSession(body);
    }

    // REST_API
    @DeleteMapping("/deleteSession")
    public void deleteSession(@RequestParam("id") Long id){
        sessionService.deleteSession(id);
    }

}