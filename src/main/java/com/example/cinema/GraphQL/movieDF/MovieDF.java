package com.example.cinema.GraphQL.movieDF;

import com.example.cinema.Entity.Movie;
import com.example.cinema.Service.MovieService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieDF implements DataFetcher<Movie> {

    final MovieService movieService;

    public MovieDF(MovieService movieService) {
        this.movieService = movieService;
    }


    @Override
    public Movie get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return movieService.getById(id);
    }
}
