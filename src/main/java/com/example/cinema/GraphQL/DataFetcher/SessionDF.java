package com.example.cinema.GraphQL.DataFetcher;


import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Hall;
import com.example.cinema.Entity.Movie;
import com.example.cinema.Entity.Session;
import com.example.cinema.Repository.HallRepository;
import com.example.cinema.Repository.MovieRepository;
import com.example.cinema.Service.SessionService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class SessionDF {

    final SessionService sessionService;
    final MovieRepository movieRepository;
    final HallRepository hallRepository;

    @Autowired
    public SessionDF(SessionService sessionService, MovieRepository movieRepository, HallRepository hallRepository) {
        this.sessionService = sessionService;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
    }

    public DataFetcher<Session> getById(){
        return env -> sessionService.getById(env.getArgument("id"));
    }

    public DataFetcher<List<Session>> getAllByMovieId(){
        return env -> sessionService.getAllByMovieId(env.getArgument("movie_id"));
    }

    public DataFetcher<List<Session>> getAllByHallId(){
        return env -> sessionService.getAllByHallId(env.getArgument("hall_id"));
    }

    public DataFetcher<Session> editSession(){
        return env -> {
            Long id = env.getArgument("id");
            if (id == null) {
                StaticMethods.createResponse(400, "Field id is incorrect");
                return null;
            }
            if (!sessionService.existsById(id)){
                StaticMethods.createResponse(400, "Session doesn`t exist with this id");
                return null;
            }

            Session session = sessionService.getById(id);
            if(env.containsArgument("start_time_of_display"))
                session.setStart_time_of_display(env.getArgument("start_time_of_display"));
            if(env.containsArgument("end_time_of_display"))
                session.setEnd_time_of_display(env.getArgument("end_time_of_display"));
            if(env.containsArgument("date"))
                session.setDate(StaticMethods.convertStringToDate(env.getArgument("date")));
            if(env.containsArgument("price"))
                session.setPrice(env.getArgument("price"));
            if(env.containsArgument("json"))
                session.setJson(env.getArgument("json"));

            if(env.containsArgument("movie_id")) {
                Long movie_id = env.getArgument("movie_id");
                if(!movieRepository.existsById(movie_id)){
                    StaticMethods.createResponse(400, "Movie doesn`t exist with this id");
                    return null;
                }
                Movie movie = movieRepository.getById(movie_id);
                session.setMovie(movie);
            }

//                session.setMovie(session.getMovie(env.getArgument("movie_id")));
//            if(env.containsArgument("hall_id"))
//                session.setHall(env.getArgument("hall_id"));
//            return sessionService.save(session);

            if(env.containsArgument("hall_id")) {
                Long hall_id = env.getArgument("hall_id");
                if(!hallRepository.existsById(hall_id)){
                    StaticMethods.createResponse(400, "Hall doesn`t exist with this id");
                    return null;
                }
                Hall hall = hallRepository.getById(hall_id);
                session.setHall(hall);
            }
            return sessionService.save(session);

        };
    }

}
