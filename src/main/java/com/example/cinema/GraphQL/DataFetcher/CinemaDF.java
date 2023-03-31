package com.example.cinema.GraphQL.DataFetcher;

import com.example.cinema.Entity.Cinema;
import com.example.cinema.Service.CinemaService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CinemaDF {

    final CinemaService cinemaService;

    @Autowired
    public CinemaDF(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    public DataFetcher<List<Cinema>> getAll(){
        return env -> cinemaService.findAll();
    }

    public DataFetcher<Cinema> getById(){
        return env -> {
            Long id = env.getArgument("id");
            return cinemaService.getById(id);
        };
    }

    public DataFetcher<Cinema> editCinema(){
        return env -> {
            Long id = env.getArgument("id");
            Cinema cinema = cinemaService.getById(id);
            if (env.containsArgument("name"))
                cinema.setName(env.getArgument("name"));
            if(env.containsArgument("address"))
                cinema.setAddress(env.getArgument("address"));
            if(env.containsArgument("rating"))
                cinema.setRating(env.getArgument("rating"));
            return cinemaService.save(cinema);
        };
    }
}
