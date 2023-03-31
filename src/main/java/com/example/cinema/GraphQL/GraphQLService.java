package com.example.cinema.GraphQL;

import com.example.cinema.GraphQL.DataFetcher.CinemaDF;
import com.example.cinema.GraphQL.DataFetcher.HallDF;
import com.example.cinema.GraphQL.DataFetcher.MovieDF;
import com.example.cinema.GraphQL.DataFetcher.ReviewDF;
import com.example.cinema.GraphQL.sessionDF.SessionDF;
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

    CinemaDF cinemaDF;
    HallDF hallDF;
    MovieDF movieDF;
    ReviewDF reviewDF;
    SessionDF sessionDF;

    @Autowired
    public GraphQLService(
            CinemaDF cinemaDF,
            HallDF hallDF,
            MovieDF movieDF,
            ReviewDF reviewDF,
            SessionDF sessionDF
    ) {
        this.cinemaDF = cinemaDF;
        this.hallDF = hallDF;
        this.movieDF = movieDF;
        this.reviewDF = reviewDF;
        this.sessionDF = sessionDF;
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
                        .dataFetcher("allCinema", cinemaDF.getAll())
                        .dataFetcher("cinema", cinemaDF.getById())
                        .dataFetcher("hall", hallDF.getById())
                        .dataFetcher("allHall", hallDF.getAll())
                        .dataFetcher("movie", movieDF.getById())
                        .dataFetcher("allMovie", movieDF.getAll())
                        .dataFetcher("allHallsByCinemaId", hallDF.getAllByCinemaId())
                        .dataFetcher("review", reviewDF.getById())
                        .dataFetcher("allReview", reviewDF.getAll())
                        .dataFetcher("session", sessionDF.getById())
                        .dataFetcher("allSessionsByHallId", sessionDF.getAllByHallId())
                        .dataFetcher("allSessionsByMovieId", sessionDF.getAllByMovieId())
                )
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher("editCinema", cinemaDF.editCinema()))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
