package com.example.cinema.Controller;

import com.example.cinema.Service.ReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // REST_API
    @PostMapping("/addReview")
    public void addReview(@RequestBody String body) {
        reviewService.addReview(body);
    }

}
