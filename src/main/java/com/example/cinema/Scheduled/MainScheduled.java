package com.example.cinema.Scheduled;

import com.example.cinema.Entity.EStatuses;
import com.example.cinema.Entity.Order;
import com.example.cinema.Entity.UserEntity;
import com.example.cinema.Service.OrderService;
import com.example.cinema.Service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainScheduled {

    @Autowired
    OrderService orderService;
    @Autowired
    UserEntityService userService;

    // Notifying users when there is an empty session in the order
    @Scheduled(cron = "0 */10 * * * *")
    private void notifyingUsers(){
        List<Order> orders = orderService.findAllByStatus(EStatuses.OPEN);
        orders.forEach(order -> {
            UserEntity user = userService.findByOrder(order.getId());
            sendWarningMessage(user, order);
        });
    }

    private void sendWarningMessage(UserEntity user, Order order) {
        // логика отправки сообщения пользователю
        System.out.println("\"Sending\" a message to the user");
    }

}
