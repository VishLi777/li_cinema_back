package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Hall;
import com.example.cinema.Entity.Movie;
import com.example.cinema.Entity.Session;
import com.example.cinema.Repository.SessionRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    HallService hallService;

    @Autowired
    MovieService movieService;

    public void addSession(String body) {
        Session session = createSessionFromJson(body);
        if(session == null)
            return;
        sessionRepository.save(session);
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Session created");
    }

    private Session createSessionFromJson(String body){

        Long startTimeOfDisplay = StaticMethods.parsingLongFromJson(body, "start_time_of_display");
        Long endTimeOfDisplay = StaticMethods.parsingLongFromJson(body, "end_time_of_display");
        Long price = StaticMethods.parsingLongFromJson(body, "price");

        String d = StaticMethods.parsingStringFromJson(body, "date");
//        Date date = new Date();
        if (d==null) {
            StaticMethods.createResponse(400, "Date is null");
            return null;
        }
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ITALIAN);
        Date date;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            StaticMethods.createResponse(400, "Incorrect date");
            return null;
        }

        String json;

        try {
            JSONObject jsonObject = new JSONObject(body);
            json = jsonObject.getJSONObject("json").toString();
        } catch (JSONException e) {
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Incorrect JSON");
            return null;
        }

        Long hallId = StaticMethods.parsingLongFromJson(body, "hall_id");
        if(!hallService.existsById(hallId)){
            StaticMethods.createResponse(400, "Hall doesn`t exist with this id");
            return null;
        }

        Long movieId = StaticMethods.parsingLongFromJson(body, "movie_id");
        if(!movieService.existsById(movieId)){
            StaticMethods.createResponse(400,"Movie doesn`t exist with this id");
            return null;
        }

        if(startTimeOfDisplay == null || endTimeOfDisplay == null || price == null){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Necessary fields are empty");
            return null;
        }

        Session session = new Session();
        session.setStart_time_of_display(startTimeOfDisplay);
        session.setEnd_time_of_display(endTimeOfDisplay);
        session.setDate(date);
        session.setPrice(price);
        session.setJson(json);

        Hall hall = hallService.getById(hallId);
        session.setHall(hall);
        Movie movie = movieService.getById(movieId);
        session.setMovie(movie);

        return session;
    }
}

