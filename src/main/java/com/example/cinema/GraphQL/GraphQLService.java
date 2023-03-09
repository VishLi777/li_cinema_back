package com.example.cinema.GraphQL;

import com.example.cinema.GraphQL.cinemaDF.AllCinemaDataFetcher;
import com.example.cinema.GraphQL.cinemaDF.CinemaDataFetcher;
import com.example.cinema.GraphQL.hallDF.AllHallDF;
import com.example.cinema.GraphQL.hallDF.AllHallsByCinemaIdDF;
import com.example.cinema.GraphQL.hallDF.HallDF;
import com.example.cinema.GraphQL.movieDF.AllMovieDF;
import com.example.cinema.GraphQL.movieDF.MovieDF;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class GraphQLService {

    AllCinemaDataFetcher allCinemaDataFetcher;
    CinemaDataFetcher cinemaDataFetcher;
    HallDF hallDF;
    AllHallDF allHallDF;
    MovieDF movieDF;
    AllMovieDF allMovieDF;
    AllHallsByCinemaIdDF allHallsByCinemaIdDF;

//    CinemaService cinemaService;

    @Autowired
    public GraphQLService(
            AllCinemaDataFetcher allCinemaDataFetcher,
            CinemaDataFetcher cinemaDataFetcher,
            HallDF hallDF,
            AllHallDF allHallDF,
            MovieDF movieDF,
            AllMovieDF allMovieDF,
            AllHallsByCinemaIdDF allHallsByCinemaIdDF

    ) {
        this.allCinemaDataFetcher = allCinemaDataFetcher;
        this.cinemaDataFetcher = cinemaDataFetcher;
        this.hallDF = hallDF;
        this.allHallDF = allHallDF;
        this.movieDF = movieDF;
        this.allMovieDF = allMovieDF;
        this.allHallsByCinemaIdDF = allHallsByCinemaIdDF;
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
                        .dataFetcher("allCinema", allCinemaDataFetcher)
                        .dataFetcher("cinema", cinemaDataFetcher)
                        .dataFetcher("hall", hallDF)
                        .dataFetcher("allHall", allHallDF)
                        .dataFetcher("movie", movieDF)
                        .dataFetcher("allMovie", allMovieDF)
                        .dataFetcher("allHallsByCinemaId", allHallsByCinemaIdDF)
                )
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
