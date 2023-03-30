package com.example.cinema.GraphQL.cinemaDF;

import com.example.cinema.Entity.Cinema;
import com.example.cinema.Service.CinemaService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllCinemaDF implements DataFetcher<List<Cinema>> {

    final CinemaService cinemaService;

    @Autowired
    public AllCinemaDF(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Override
    public List<Cinema> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return cinemaService.findAll();
    }
}
