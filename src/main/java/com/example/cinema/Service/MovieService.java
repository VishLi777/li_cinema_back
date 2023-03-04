package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Movie;

import com.example.cinema.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

//    @Autowired
//    SessionService sessionService;

    public void addMovie(String body) {
        Movie movie = createMovieFromJson(body);
        if (movie == null)
            return;
        movieRepository.save(movie);
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Movie created");
    }

    private Movie createMovieFromJson(String body){
        String name = StaticMethods.parsingStringFromJson(body, "name");
        String author = StaticMethods.parsingStringFromJson(body, "author");
        Long duration = StaticMethods.parsingLongFromJson(body, "duration");
        Long start_date_of_rental = StaticMethods.parsingLongFromJson(body, "start_date_of_rental");
        Long end_date_of_rental = StaticMethods.parsingLongFromJson(body, "end_date_of_rental");

        if(name == null || author == null || duration == null || start_date_of_rental == null || end_date_of_rental == null){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Necessary fields are empty");
            return null;
        }

//        Long sessionId = StaticMethods.parsingLongFromJson(body, "session_id");

        Movie movie = new Movie();
        movie.setName(name);
        movie.setAuthor(author);
        movie.setDuration(duration);
        movie.setStart_date_of_rental(start_date_of_rental);
        movie.setEnd_date_of_rental(end_date_of_rental);

//        Session session = sessionService.getById(sessionId);
//        movie.setSession(session);

        return movie;
    }

    public Movie getById(Long movie_id) {
        return movieRepository.getById(movie_id);
    }

    public boolean existsById(Long movie_id) {
        return movieRepository.existsById(movie_id);
    }
}
