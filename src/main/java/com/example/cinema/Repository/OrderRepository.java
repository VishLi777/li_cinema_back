package com.example.cinema.Repository;

import com.example.cinema.Entity.EStatuses;
import com.example.cinema.Entity.Order;
import com.example.cinema.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllBySession(Session session);
    List<Order> findAllByStatus(EStatuses status);

}