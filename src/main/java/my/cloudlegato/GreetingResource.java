package my.cloudlegato;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Path( "/hello" )
public class GreetingResource {

    @Inject
    Keycloak keycloak;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Blocking
    public Uni< Response > hello( ) {

        // Select the realm
        return Uni.createFrom( ).item( keycloak.realms( ).realm( "klclc" ) )
                .flatMap( realmResource -> {

                    // Get the users resource
                    return Uni.createFrom( ).item( realmResource.users( ) )
                            .flatMap( users -> {

                                // Count all users in the realm
                                return Uni.createFrom( ).item( users.count( ) )
                                        .map( count -> {
                                            JsonbConfig jsonbConfig = ( new JsonbConfig( ) ).withNullValues( true ).withEncoding( StandardCharsets.UTF_8.name( ) );
                                            Jsonb jsonb = JsonbBuilder.create( jsonbConfig );

                                            return Response.status( Response.Status.OK )
                                                    .entity( jsonb.toJson( new HashMap<>( ) {{
                                                        put( "count", count );
                                                    }} ) )
                                                    .build( );
                                        } );
                            } );
                } );
    }

}
