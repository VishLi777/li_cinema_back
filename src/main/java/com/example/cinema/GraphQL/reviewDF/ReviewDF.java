package com.example.cinema.GraphQL.reviewDF;


import com.example.cinema.Entity.Movie;
import com.example.cinema.Entity.Review;

import com.example.cinema.Service.ReviewService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewDF implements DataFetcher<Review>  {
    @Autowired
    final ReviewService reviewService;


    public ReviewDF(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public Review get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return reviewService.getById(id);
    }
}
