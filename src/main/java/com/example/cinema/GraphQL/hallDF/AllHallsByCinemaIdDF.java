package com.example.cinema.GraphQL.hallDF;

import com.example.cinema.Entity.Hall;
import com.example.cinema.Service.HallService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllHallsByCinemaIdDF implements DataFetcher<List<Hall>> {

    final HallService hallService;

    @Autowired
    public AllHallsByCinemaIdDF(HallService hallService) {
        this.hallService = hallService;
    }


    @Override
    public List<Hall> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long cinema_id = dataFetchingEnvironment.getArgument("cinema_id");
        return hallService.getAllByCinemaId(cinema_id);

    }
}
