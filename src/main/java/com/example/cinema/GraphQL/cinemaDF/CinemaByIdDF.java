package com.example.cinema.GraphQL.cinemaDF;

import com.example.cinema.Entity.Cinema;
import com.example.cinema.Service.CinemaService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

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

    public DataFetcher<Cinema> editCinema(){
        return env -> {
            Long id = ((Double) env.getArgument("id")).longValue();
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
