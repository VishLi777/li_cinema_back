package com.example.cinema.Controller;


import com.example.cinema.Service.SessionService;
import org.springframework.web.bind.annotation.*;

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

    // REST_API
    @PostMapping("/deleteSession")
    public void deleteSession(@RequestParam("id") Long id){
        sessionService.deleteSession(id);
    }
}