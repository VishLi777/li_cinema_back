package com.example.cinema.Repository;

import com.example.cinema.Entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}
