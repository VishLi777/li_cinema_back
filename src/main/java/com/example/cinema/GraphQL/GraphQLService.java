package com.example.cinema.GraphQL;

import com.example.cinema.GraphQL.cinemaDF.AllCinemaDF;
import com.example.cinema.GraphQL.cinemaDF.CinemaByIdDF;
import com.example.cinema.GraphQL.hallDF.AllHallDF;
import com.example.cinema.GraphQL.hallDF.AllHallsByCinemaIdDF;
import com.example.cinema.GraphQL.hallDF.HallByIdDF;
import com.example.cinema.GraphQL.movieDF.AllMovieDF;
import com.example.cinema.GraphQL.movieDF.MovieByIdDF;
import com.example.cinema.GraphQL.reviewDF.AllReviewDF;
import com.example.cinema.GraphQL.reviewDF.ReviewByIdDF;
import com.example.cinema.GraphQL.sessionDF.AllSessionsByHallIdDF;
import com.example.cinema.GraphQL.sessionDF.AllSessionsByMovieIdDF;
import com.example.cinema.GraphQL.sessionDF.SessionByIdDF;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class GraphQLService {

    AllCinemaDF allCinemaDF;
    CinemaByIdDF cinemaByIdDF;
    HallByIdDF hallByIdDF;
    AllHallDF allHallDF;
    AllHallsByCinemaIdDF allHallsByCinemaIdDF;
    MovieByIdDF movieByIdDF;
    AllMovieDF allMovieDF;
    ReviewByIdDF reviewByIdDF;
    AllReviewDF allReviewDF;
    SessionByIdDF sessionByIdDF;
    AllSessionsByHallIdDF allSessionsByHallIdDF;
    AllSessionsByMovieIdDF allSessionsByMovieIdDF;

    @Autowired
    public GraphQLService(
            AllCinemaDF allCinemaDF,
            CinemaByIdDF cinemaByIdDF,
            HallByIdDF hallByIdDF,
            AllHallDF allHallDF,
            MovieByIdDF movieByIdDF,
            AllMovieDF allMovieDF,
            AllHallsByCinemaIdDF allHallsByCinemaIdDF,
            ReviewByIdDF reviewByIdDF,
            AllReviewDF allReviewDF,
            SessionByIdDF sessionByIdDF,
            AllSessionsByHallIdDF allSessionsByHallIdDF,
            AllSessionsByMovieIdDF allSessionsByMovieIdDF

    ) {
        this.allCinemaDF = allCinemaDF;
        this.cinemaByIdDF = cinemaByIdDF;
        this.hallByIdDF = hallByIdDF;
        this.allHallDF = allHallDF;
        this.movieByIdDF = movieByIdDF;
        this.allMovieDF = allMovieDF;
        this.allHallsByCinemaIdDF = allHallsByCinemaIdDF;
        this.reviewByIdDF = reviewByIdDF;
        this.allReviewDF = allReviewDF;
        this.sessionByIdDF = sessionByIdDF;
        this.allSessionsByHallIdDF = allSessionsByHallIdDF;
        this.allSessionsByMovieIdDF = allSessionsByMovieIdDF;
    }

    private GraphQL graphQL;

    @PostConstruct
    private void loadSchema() {
        File schemaFile = new File(System.getProperty("user.dir").replace('\\', '/') + "/src/main/resources/config.graphql");
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allCinema", allCinemaDF)
                        .dataFetcher("cinema", cinemaByIdDF)
                        .dataFetcher("hall", hallByIdDF)
                        .dataFetcher("allHall", allHallDF)
                        .dataFetcher("movie", movieByIdDF)
                        .dataFetcher("allMovie", allMovieDF)
                        .dataFetcher("allHallsByCinemaId", allHallsByCinemaIdDF)
                        .dataFetcher("review", reviewByIdDF)
                        .dataFetcher("allReview", allReviewDF)
                        .dataFetcher("session", sessionByIdDF)
                        .dataFetcher("allSessionsByHallId", allSessionsByHallIdDF)
                        .dataFetcher("allSessionsByMovieId", allSessionsByMovieIdDF)
                )
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher("editCinema", cinemaByIdDF.editCinema()))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
