package com.example.cinema.Controller;

import com.example.cinema.Service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    final private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // REST_API
    @PostMapping("/addOrder")
    public void addOrder(@RequestBody String body){
        orderService.addOrder(body);
    }

    // REST_API
    @PostMapping("/deleteOrder")
    public void deleteOrder(@RequestParam("id") Long id){
        orderService.deleteOrder(id);
    }
}
