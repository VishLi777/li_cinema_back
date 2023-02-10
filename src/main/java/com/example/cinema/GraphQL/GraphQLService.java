package com.example.cinema.GraphQL;

import com.example.cinema.Service.CinemaService;
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

    AllCinemaDataFetcher allCinemaDataFetcher;
    CinemaDataFetcher cinemaDataFetcher;

    CinemaService cinemaService;

    @Autowired
    public GraphQLService(
            AllCinemaDataFetcher allCinemaDataFetcher,
            CinemaDataFetcher cinemaDataFetcher,
            CinemaService cinemaService
    ) {
        this.allCinemaDataFetcher = allCinemaDataFetcher;
        this.cinemaDataFetcher = cinemaDataFetcher;
        this.cinemaService = cinemaService;
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
                )
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
