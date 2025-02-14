package com.example.cinema.Controller;

import com.example.cinema.Entity.Hall;
import com.example.cinema.GraphQL.GraphQLService;
import com.example.cinema.Service.HallService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hall")
public class HallController {

    @Autowired
    HallService hallService;
    @Autowired
    GraphQLService graphQLService;

    // REST_API
    @PostMapping("/addHall")
    public void addHall(@RequestBody String body){
        hallService.addHall(body);
    }

    // REST_API
    @DeleteMapping("/deleteHall")
    public void deleteHall(@RequestParam("id") Long id){
        hallService.deleteHall(id);
    }

}
