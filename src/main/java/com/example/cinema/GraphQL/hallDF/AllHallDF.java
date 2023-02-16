package com.example.cinema.GraphQL.hallDF;

import com.example.cinema.Entity.Hall;
import com.example.cinema.Service.HallService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllHallDF implements DataFetcher<List<Hall>> {

    final HallService hallService;

    @Autowired
    public AllHallDF(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public List<Hall> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return hallService.getAll();
    }
}
