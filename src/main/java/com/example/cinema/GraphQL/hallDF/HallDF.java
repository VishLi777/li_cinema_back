package com.example.cinema.GraphQL.hallDF;

import com.example.cinema.Entity.Hall;
import com.example.cinema.Service.HallService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HallDF implements DataFetcher<Hall> {

    final HallService hallService;

    @Autowired
    public HallDF(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public Hall get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return hallService.getById(id);
    }
}
