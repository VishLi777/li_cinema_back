package com.example.cinema.GraphQL.reviewDF;

import com.example.cinema.Entity.Movie;
import com.example.cinema.Entity.Review;
import com.example.cinema.Service.ReviewService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllReviewDF implements DataFetcher<List<Review>> {


    final ReviewService reviewService;

    @Autowired
    public AllReviewDF(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public List<Review> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return reviewService.getAll();
    }
}
