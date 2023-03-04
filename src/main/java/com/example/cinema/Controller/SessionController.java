package com.example.cinema.Controller;


import com.example.cinema.Service.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // REST_API
    @PostMapping("/addSession")
    public void addSession(@RequestBody String body) {
        sessionService.addSession(body);
    }

}