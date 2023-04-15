package com.example.cinema.GraphQL.DataFetcher;


import com.example.cinema.Entity.EStatuses;
import com.example.cinema.Entity.Order;
import com.example.cinema.Service.OrderService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDF {

    final OrderService orderService;

    @Autowired
    public OrderDF(OrderService orderService) {
        this.orderService = orderService;
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
}

