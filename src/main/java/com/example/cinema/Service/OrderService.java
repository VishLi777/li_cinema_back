package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;

import com.example.cinema.Entity.Order;
import com.example.cinema.Entity.Session;
import com.example.cinema.Entity.UserEntity;
import com.example.cinema.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserEntityService userService;

    @Autowired
    SessionService sessionService;

    public void addOrder(String body) {
        Order order = createOrderFromJson(body);
        if (order == null)
            return;
        orderRepository.save(order);
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Order created");
    }

    private Order createOrderFromJson(String body) {
        String final_price = StaticMethods.parsingStringFromJson(body, "final_price");

        if(final_price == null){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Necessary fields are empty");
            return null;
        }

        Long user_id = StaticMethods.parsingLongFromJson(body, "user_id");
        if(!userService.existsById(user_id)){
            StaticMethods.createResponse(400, "User doesn`t exist with this id");
            return null;
        }

        Long session_id = StaticMethods.parsingLongFromJson(body, "session_id");
        if(!sessionService.existsById(session_id)){
            StaticMethods.createResponse(400, "Session doesn`t exist with this id");
            return null;
        }

        Order order = new Order();
        order.setFinal_price(final_price);
        UserEntity user = userService.getById(user_id);
        order.setUser(user);
        Session session = sessionService.getById(session_id);
        order.setSession(session);
        return order;
    }

}
