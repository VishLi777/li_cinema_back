package com.example.cinema.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ci_session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time_of_display")
    private Long start_time_of_display;

    @Column(name = "end_time_of_display")
    private Long end_time_of_display;

    @Column(name = "data")
    private Date date;

    @Column(name = "price")
    private Long price;

    @Column(name = "json")
    private String json;

    @OneToMany(mappedBy = "session")
    private List<Order> orders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id")
    private Hall hall;

}
