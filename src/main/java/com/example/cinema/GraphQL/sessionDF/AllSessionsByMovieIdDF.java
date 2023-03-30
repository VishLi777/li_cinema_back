package com.example.cinema.GraphQL.sessionDF;


import com.example.cinema.Entity.Session;
import com.example.cinema.Service.SessionService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllSessionsByMovieIdDF implements DataFetcher<List<Session>> {

    final SessionService sessionService;

    @Autowired
    public AllSessionsByMovieIdDF(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @Override
    public List<Session> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long movie_id = dataFetchingEnvironment.getArgument("movie_id");
        return sessionService.getAllByMovieId(movie_id);

    }
}
