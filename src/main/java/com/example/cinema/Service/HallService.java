package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Cinema;
import com.example.cinema.Entity.Hall;
import com.example.cinema.Repository.HallRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class HallService {

    @Autowired
    HallRepository hallRepository;
    @Autowired
    CinemaService cinemaService;


    public void addHall(String body) {
        Long cinema_id = StaticMethods.parsingLongFromJson(body, "cinema_id");
        Long number = StaticMethods.parsingLongFromJson(body, "number");
        String json;

        try {
            JSONObject jsonObject = new JSONObject(body);
            json = jsonObject.getJSONObject("json").toString();
        } catch (JSONException e) {
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Incorrect JSON");
            return;
        }

        if(cinema_id == null || number == null || json == null){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Incorrect JSON");
            return;
        }

//        int cinema_id = Integer.parseInt(strCinema_id);
//        int number = Integer.parseInt(strNumber);

        if(!cinemaService.existsById((long) cinema_id)){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Cinema with this id doesn't exist");
            return;
        }

        Cinema cinema = cinemaService.getById((long) cinema_id);

        Hall hall = new Hall();
        hall.setNumber(Math.toIntExact(number));
        hall.setJson(json);
        hall.setCinema(cinema);
        hallRepository.save(hall);
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Hall created");
    }

    public Hall getById(Long id) {
        return hallRepository.getById(id);
    }



    public List<Hall> getAll() {
        return hallRepository.findAll();
    }

    public boolean existsById(Long hall_id) {
        return hallRepository.existsById(hall_id);
    }

    public void deleteHall(Long id) {
        if (id == null) {
            return;
        }
        if(hallRepository.existsById(id))
            hallRepository.deleteById(id);
        else {
            StaticMethods.createResponse(400, "Hall doesn`t exist with this id");
            return;
        }
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Hall deleted");
    }

    public List<Hall> getAllByCinemaId(Long cinema_id) {
        Cinema cinema = cinemaService.getById(cinema_id);
        return hallRepository.getAllByCinema(cinema);
    }
}
