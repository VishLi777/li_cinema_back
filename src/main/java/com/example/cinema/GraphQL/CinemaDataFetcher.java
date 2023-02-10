package com.example.cinema.GraphQL;

import com.example.cinema.Entity.Cinema;
import com.example.cinema.Service.CinemaService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CinemaDataFetcher implements DataFetcher<Cinema> {

    final CinemaService cinemaService;

    @Autowired
    public CinemaDataFetcher(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public Cinema get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return cinemaService.getById(id);
    }
}
