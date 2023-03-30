package com.example.cinema.GraphQL.sessionDF;


import com.example.cinema.Entity.Session;
import com.example.cinema.Service.SessionService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionByIdDF implements DataFetcher<Session> {

    final SessionService sessionService;

    @Autowired
    public SessionByIdDF(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public Session get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return sessionService.getById(id);
    }
}
