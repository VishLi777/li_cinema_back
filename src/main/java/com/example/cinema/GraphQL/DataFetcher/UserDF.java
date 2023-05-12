package com.example.cinema.GraphQL.DataFetcher;

import com.example.cinema.Entity.UserEntity;
import com.example.cinema.Service.UserService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDF {

    @Autowired
    final UserService userService;

    public UserDF(UserService userService) {
        this.userService = userService;
    }

    public DataFetcher<UserEntity> getById(){
        return env -> userService.getById(env.getArgument("id"));
    }
}
