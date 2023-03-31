package com.example.cinema.GraphQL.sessionDF;


import com.example.cinema.Entity.Session;
import com.example.cinema.Service.SessionService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionDF {

    final SessionService sessionService;

    @Autowired
    public SessionDF(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public DataFetcher<Session> getById(){
        return env -> sessionService.getById(env.getArgument("id"));
    }

    public DataFetcher<List<Session>> getAllByMovieId(){
        return env -> sessionService.getAllByMovieId(env.getArgument("movie_id"));
    }

    public DataFetcher<List<Session>> getAllByHallId(){
        return env -> sessionService.getAllByHallId(env.getArgument("hall_id"));
    }

}
