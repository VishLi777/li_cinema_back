package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Cinema;
import com.example.cinema.Repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    public void addCinema(String body) {
        Cinema cinema = createCinemaFromJson(body);
        cinemaRepository.save(cinema);
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Cinema created");
    }

    private Cinema createCinemaFromJson(String body){
        String name = StaticMethods.parsingStringFromJson(body, "name");
        String address = StaticMethods.parsingStringFromJson(body, "address");
        String temp;
        Double rating = (temp = StaticMethods.parsingStringFromJson(body, "rating")) == null ? null : Double.valueOf(temp);

        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setAddress(address);
        cinema.setRating(rating);
        return cinema;
    }


    public Cinema getById(Long id) {
        return cinemaRepository.getById(id);
    }

    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    public Boolean existsById(Long id){
        return cinemaRepository.existsById(id);
    }

    public void deleteCinema(Long id) {
        if (id == null) {
            return;
        }
        if(cinemaRepository.existsById(id))
            cinemaRepository.deleteById(id);
        else {
            StaticMethods.createResponse(400, "Cinema doesn`t exist with this id");
            return;
        }

        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Cinema deleted");
    }
}
