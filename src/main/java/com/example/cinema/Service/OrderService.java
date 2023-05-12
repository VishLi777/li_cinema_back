package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;

import com.example.cinema.Entity.*;
import com.example.cinema.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        String json = StaticMethods.parsingStringFromJson(body, "json");
        String status_temp = StaticMethods.parsingStringFromJson(body, "status");

        if(final_price == null || json == null || status_temp == null){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Necessary fields are empty");
            return null;
        }

        EStatuses status = EStatuses.valueOf(status_temp);
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



        Session session = sessionService.getById(session_id);

        if(!sessionService.updatingPlaces(session, json, user_id))
            return null;


        Order order = new Order();
        order.setFinal_price(final_price);
        order.setStatus(status);
        UserEntity user = userService.getById(user_id);
        order.setUser(user);
        order.setSession(session);
        return order;
    }

//    public void deleteOrder(String id) {
//        Order order = deleteOrderFromJson(id);
//        if (order == null)
//            return;
//        orderRepository.delete(order);
//        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Order deleted");
//    }
//    private Order deleteOrderFromJson(String id) {
//        Long order_id = StaticMethods.parsingLongFromJson(id, "id");
//        if (order_id == null) {
//            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Necessary fields are empty");
//            return null;
//        }
//
//        Order order = orderRepository.getById(order_id);
//        return order;
//    }

    public void deleteOrder(Long id) {
        if (id == null) {
            return;
        }
        if(orderRepository.existsById(id))
            orderRepository.deleteById(id);
        else {
            StaticMethods.createResponse(400, "Order doesn`t exist with this id");
            return;
        }
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Order deleted");
    }

    public List<Order> findAllBySession(Session session){
        return orderRepository.findAllBySession(session);
    }

    public void saveAll(List<Order> orders) {
        orderRepository.saveAll(orders);
    }

    public List<Order> findAllByStatus(EStatuses status){
        return orderRepository.findAllByStatus(status);
    }

    public Order getById(Long id) {
        return orderRepository.getById(id);
    }


    public List<Order> getAllByUserIdAndOrderStatus(Long user_id, EStatuses status) {
        UserEntity user = userService.getById(user_id);
        return orderRepository.getAllByUserAndStatus(user, status);

    }

    public boolean existsById(Long session_id) {
        return orderRepository.existsById(session_id);
    }
    public Order save(Order order){
        return orderRepository.save(order);
    }


}
