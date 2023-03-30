package com.example.cinema.GraphQL.sessionDF;


import com.example.cinema.Entity.Session;
import com.example.cinema.Service.SessionService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllSessionsByHallIdDF implements DataFetcher<List<Session>> {

    final SessionService sessionService;

    @Autowired
    public AllSessionsByHallIdDF(SessionService sessionService) {
        this.sessionService = sessionService;
    }


    @Override
    public List<Session> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long hall_id = dataFetchingEnvironment.getArgument("hall_id");
        return sessionService.getAllByHallId(hall_id);

    }
}