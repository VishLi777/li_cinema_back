package com.example.cinema.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ci_hall")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "json")
    private String json;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Session> session;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;


}
