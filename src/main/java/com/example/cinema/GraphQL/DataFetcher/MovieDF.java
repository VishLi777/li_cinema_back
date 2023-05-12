package com.example.cinema.GraphQL.DataFetcher;

import com.example.cinema.Entity.Hall;
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

    public DataFetcher<Movie> editMovie(){
        return env -> {
            Long id = env.getArgument("id");
            Movie movie = movieService.getById(id);
            if(env.containsArgument("name"))
                movie.setName(env.getArgument("name"));
            if(env.containsArgument("author"))
                movie.setAuthor(env.getArgument("author"));
            if(env.containsArgument("duration"))
                movie.setDuration(env.getArgument("duration"));
            if(env.containsArgument("start_date_of_rental"))
                movie.setStart_date_of_rental(env.getArgument("start_date_of_rental"));
            if(env.containsArgument("end_date_of_rental"))
                movie.setEnd_date_of_rental(env.getArgument("end_date_of_rental"));
            return movieService.save(movie);
        };
    }
}
