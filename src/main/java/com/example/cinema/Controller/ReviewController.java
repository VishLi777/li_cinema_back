package com.example.cinema.Controller;

import com.example.cinema.Service.ReviewService;
import org.springframework.web.bind.annotation.*;

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

    // REST_API
    @PostMapping("/deleteReview")
    public void deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
    }

}
