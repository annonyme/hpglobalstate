package de.hannespries.globalstate.server;

import de.hannespries.globalstate.Action;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("globalstate")
@ApplicationScoped
public class RESTController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> action(Action action){
        return Scope.state.action(action);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(){
        return "{\"online\": true}";
    }
}
