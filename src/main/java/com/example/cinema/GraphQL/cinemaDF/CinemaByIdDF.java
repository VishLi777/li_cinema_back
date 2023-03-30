package com.example.cinema.GraphQL.cinemaDF;

import com.example.cinema.Entity.Cinema;
import com.example.cinema.Service.CinemaService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CinemaByIdDF implements DataFetcher<Cinema> {

    final CinemaService cinemaService;

    @Autowired
    public CinemaByIdDF(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public Cinema get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return cinemaService.getById(id);
    }
}
