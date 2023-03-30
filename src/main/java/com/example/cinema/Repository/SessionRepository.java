package com.example.cinema.Repository;

import com.example.cinema.Entity.Cinema;
import com.example.cinema.Entity.Hall;
import com.example.cinema.Entity.Movie;
import com.example.cinema.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

    List<Session> getAllByHall(Hall hall);
    List<Session> getAllByMovie(Movie movie);
}
