package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Cinema;
import com.example.cinema.Entity.Review;
import com.example.cinema.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CinemaService cinemaService;

    public void addReview(String body) {
        Review review = createReviewFromJson(body);
        reviewRepository.save(review);
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Review created");
    }

    private Review createReviewFromJson(String body){
        String description = StaticMethods.parsingStringFromJson(body, "description");
        Long cinemaId = StaticMethods.parsingLongFromJson(body, "cinema_id");

        Review review = new Review();
        review.setDescription(description);

        Cinema cinema = cinemaService.getById(cinemaId);
        review.setCinema(cinema);

        return review;
    }


//    public Cinema getById(Long id) {
//        return cinemaRepository.getById(id);
//    }
//
//    public List<Cinema> findAll() {
//        return cinemaRepository.findAll();
//    }
//
//    public Boolean existsById(Long id){
//        return cinemaRepository.existsById(id);
//    }
}