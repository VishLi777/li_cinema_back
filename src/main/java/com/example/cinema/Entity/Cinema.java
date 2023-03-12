package com.example.cinema.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ci_cinema")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "rating")
    private Double rating;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<Hall> halls;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<Review> reviews;

}
