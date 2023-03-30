package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Cinema;
import com.example.cinema.Entity.Hall;
import com.example.cinema.Entity.Movie;
import com.example.cinema.Entity.Session;
import com.example.cinema.Repository.SessionRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

        Long start_time_of_display = StaticMethods.parsingLongFromJson(body, "start_time_of_display");
        Long end_time_of_display = StaticMethods.parsingLongFromJson(body, "end_time_of_display");
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

        Long hall_id = StaticMethods.parsingLongFromJson(body, "hall_id");
        if(!hallService.existsById(hall_id)){
            StaticMethods.createResponse(400, "Hall doesn`t exist with this id");
            return null;
        }

        Long movie_id = StaticMethods.parsingLongFromJson(body, "movie_id");
        if(!movieService.existsById(movie_id)){
            StaticMethods.createResponse(400,"Movie doesn`t exist with this id");
            return null;
        }

        if(start_time_of_display == null || end_time_of_display == null || price == null){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Necessary fields are empty");
            return null;
        }

        Session session = new Session();
        session.setStart_time_of_display(start_time_of_display);
        session.setEnd_time_of_display(end_time_of_display);
        session.setDate(date);
        session.setPrice(price);

        Hall hall = hallService.getById(hall_id);
        session.setHall(hall);
        Movie movie = movieService.getById(movie_id);
        session.setMovie(movie);

        session.setJson(hall.getJson());
        return session;
    }

    public boolean existsById(Long session_id) {
        return sessionRepository.existsById(session_id);
    }

    public Session getById(Long session_id) {
        return sessionRepository.getById(session_id);
    }

    public List<Session> getAll() {
        return sessionRepository.findAll();
    }

    public List<Session> getAllByHallId(Long hall_id) {
        Hall hall = hallService.getById(hall_id);
        return sessionRepository.getAllByHall(hall);
    }

    public List<Session> getAllByMovieId(Long movie_id) {
        Movie movie = movieService.getById(movie_id);
        return sessionRepository.getAllByMovie(movie);
    }

    public boolean updatingPlaces(Session session, String json, Long user_id) {

        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject sessionJson = new JSONObject(session.getJson());
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long row = jsonObject.getLong("row");
                long place = jsonObject.getLong("place");
                if(!updateOnePlace(String.valueOf(row), String.valueOf(place), sessionJson, user_id))
                    return false;
            }
            session.setJson(sessionJson.toString());
            save(session);
        } catch (JSONException e) {
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Incorrect JSON");
            return false;
        }

        return true;
    }

    private boolean updateOnePlace(String row, String place, JSONObject sessionJson, Long user_id) throws JSONException {
        JSONObject rowObject;
        try {
            rowObject = sessionJson.getJSONObject(row);
        }catch (JSONException e) {
            StaticMethods.createResponse(400, String.format("Row = %s doesn't exists in this session", row));
            return false;
        }

        long userIdInPlace;
        try {
            userIdInPlace = rowObject.getLong(place);
        }catch (JSONException e) {
            StaticMethods.createResponse(400, String.format("Place = %s doesn't exists in this session", place));
            return false;
        }

        if(userIdInPlace != 0){
            StaticMethods.createResponse(400, String.format("Place = %s cannot be booked", place));
            return false;
        }

        rowObject.putOpt(place, user_id);
        sessionJson.putOpt(row, rowObject);
        return true;
    }

    public void save(Session session){
        sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        if (id == null) {
            return;
        }
        if(sessionRepository.existsById(id))
            sessionRepository.deleteById(id);
        else {
            StaticMethods.createResponse(400, "Session doesn`t exist with this id");
            return;
        }
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Session deleted");
    }



}

