package com.example.cinema.Service;

import com.example.cinema.Dops.StaticMethods;
import com.example.cinema.Entity.Cinema;
import com.example.cinema.Entity.Movie;
import com.example.cinema.Entity.Review;
import com.example.cinema.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        Long cinema_id = StaticMethods.parsingLongFromJson(body, "cinema_id");

        if(!cinemaService.existsById(cinema_id)){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Cinema with this id doesn't exist");
            return null;
        }

        if(description == null){
            StaticMethods.createResponse(HttpServletResponse.SC_BAD_REQUEST, "Necessary field is empty");
            return null;
        }


        Review review = new Review();
        review.setDescription(description);

        Cinema cinema = cinemaService.getById(cinema_id);
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

    public void deleteReview(Long id) {
        if (id == null) {
            return;
        }
        if(reviewRepository.existsById(id))
            reviewRepository.deleteById(id);
        else {
            StaticMethods.createResponse(400, "Review doesn`t exist with this id");
            return;
        }
        StaticMethods.createResponse(HttpServletResponse.SC_CREATED, "Review deleted");
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Review getById(Long id) {
        return reviewRepository.getById(id);
    }


}