package com.example.cinema.GraphQL.movieDF;

import com.example.cinema.Entity.Movie;
import com.example.cinema.Service.MovieService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllMovieDF implements DataFetcher<List<Movie>> {

    final MovieService movieService;

    @Autowired
    public AllMovieDF(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public List<Movie> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return movieService.getAll();
    }

}
