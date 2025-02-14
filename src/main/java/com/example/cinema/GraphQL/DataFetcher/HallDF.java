package com.example.cinema.GraphQL.DataFetcher;

import com.example.cinema.Entity.Cinema;
import com.example.cinema.Entity.Hall;
import com.example.cinema.Service.HallService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HallDF {

    final HallService hallService;

    @Autowired
    public HallDF(HallService hallService) {
        this.hallService = hallService;
    }


    public DataFetcher<Hall> getById(){
        return env -> hallService.getById(env.getArgument("id"));
    }

    public DataFetcher<List<Hall>> getAllByCinemaId(){
        return env -> hallService.getAllByCinemaId(env.getArgument("cinema_id"));
    }

    public DataFetcher<List<Hall>> getAll(){
        return env -> hallService.getAll();
    }


    public DataFetcher<Hall> editHall(){
        return env -> {
            Long id = env.getArgument("id");
            Hall hall = hallService.getById(id);
            if (env.containsArgument("json"))
                hall.setJson(env.getArgument("json"));
            if(env.containsArgument("number"))
                hall.setNumber(env.getArgument("number"));
            return hallService.save(hall);
        };
    }

}
