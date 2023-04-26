package com.example.cinema.GraphQL.DataFetcher;


import com.example.cinema.Entity.Hall;
import com.example.cinema.Entity.Review;
import com.example.cinema.Service.ReviewService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewDF {

    @Autowired
    final ReviewService reviewService;

    public ReviewDF(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public DataFetcher<Review> getById(){
        return env -> reviewService.getById(env.getArgument("id"));
    }

    public DataFetcher<List<Review>> getAll(){
        return env -> reviewService.getAll();
    }

    public DataFetcher<Review> editReview(){
        return env -> {
            Long id = env.getArgument("id");
            Review review = reviewService.getById(id);
            if (env.containsArgument("description"))
                review.setDescription(env.getArgument("description"));
            return reviewService.save(review);
        };
    }

}
