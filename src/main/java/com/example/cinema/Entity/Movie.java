package com.example.cinema.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ci_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "start_date_of_rental")
    private Long start_date_of_rental;

    @Column(name = "end_date_of_rental")
    private Long end_date_of_rental;

    @OneToMany(mappedBy = "movie")
    private List<Session> sessions;

}
