package de.hannespries.globalstate.server;

import de.hannespries.globalstate.Action;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("globalstate")
public class RESTController {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, Object> action(Action action){
        return Scope.state.action(action);
    }

    @GET
    public String get(){
        return "{\"online\": true}";
    }
}
