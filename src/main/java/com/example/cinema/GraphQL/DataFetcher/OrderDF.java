package com.example.cinema.GraphQL.DataFetcher;


import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.*;
import com.example.cinema.Repository.SessionRepository;
import com.example.cinema.Repository.UserRepository;
import com.example.cinema.Service.OrderService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDF {

    final OrderService orderService;
    final UserRepository userRepository;
    final SessionRepository sessionRepository;

    @Autowired
    public OrderDF(OrderService orderService, UserRepository userRepository, SessionRepository sessionRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }


    public DataFetcher<Order> getById(){
        return env -> orderService.getById(env.getArgument("id"));
    }

    public DataFetcher<List<Order>> getAllByStatus(){
        return env -> {
            String status = env.getArgument("status");
            EStatuses eStatus = EStatuses.valueOf(status);
            return orderService.findAllByStatus(eStatus);
        };
    }

    public DataFetcher<List<Order>> getAllByUserIdAndOrderStatus(){
        return env -> {
            String status = env.getArgument("status");
            EStatuses eStatus = EStatuses.valueOf(status);
            Long user = env.getArgument("user_id");
            return orderService.getAllByUserIdAndOrderStatus(user, eStatus);
        };
    }

    public DataFetcher<Order> editOrder(){
        return env -> {
            Long id = env.getArgument("id");
            if (id == null) {
                StaticMethods.createResponse(400, "Field id is incorrect");
                return null;
            }
            if (!orderService.existsById(id)){
                StaticMethods.createResponse(400, "Order does not exist with this id");
                return null;
            }

            Order order = orderService.getById(id);
            if(env.containsArgument("final_price"))
                order.setFinal_price(env.getArgument("final_price"));
            if(env.containsArgument("status"))
                order.setStatus(EStatuses.valueOf(env.getArgument("status")));
            if(env.containsArgument("user_id")) {
                Long user_id = env.getArgument("user_id");
                if(!userRepository.existsById(user_id)){
                    StaticMethods.createResponse(400, "User does not exist with this id");
                    return null;
                }
                UserEntity user = userRepository.getById(user_id);
                order.setUser(user);
            }
            if(env.containsArgument("session_id")) {
                Long session_id = env.getArgument("session_id");
                if(!sessionRepository.existsById(session_id)){
                    StaticMethods.createResponse(400, "Session does not exist with this id");
                    return null;
                }
                Session session = sessionRepository.getById(session_id);
                order.setSession(session);
            }
            return orderService.save(order);

        };
    }




}

