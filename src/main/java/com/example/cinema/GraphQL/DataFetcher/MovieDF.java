package com.example.cinema.GraphQL.DataFetcher;

import com.example.cinema.Entity.Movie;
import com.example.cinema.Service.MovieService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieDF {

    final MovieService movieService;

    public MovieDF(MovieService movieService) {
        this.movieService = movieService;
    }

    public DataFetcher<Movie> getById(){
        return env -> movieService.getById(env.getArgument("id"));
    }

    public DataFetcher<List<Movie>> getAll(){
        return env -> movieService.getAll();
    }

}
