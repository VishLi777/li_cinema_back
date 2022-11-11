package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Cinema;
import com.example.cinema.Entity.UserEntity;
import com.example.cinema.Repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

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
        String name = StaticMethods.parsingJson(body, "name");
        String address = StaticMethods.parsingJson(body, "address");
//        Double rating = Double.valueOf(StaticMethods.parsingJson(body, "rating"));

        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setAddress(address);
        cinema.setRating(null);
        return cinema;
    }


}
