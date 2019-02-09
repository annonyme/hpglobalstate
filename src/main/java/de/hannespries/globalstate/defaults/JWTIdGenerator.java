package de.hannespries.globalstate.defaults;

import de.hannespries.globalstate.Action;
import de.hannespries.globalstate.IdGenerator;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Map;
import java.util.UUID;

public class JWTIdGenerator implements IdGenerator {
    private Key secret;
    //private long valid10Minutes = 1000 * 60 * 10;

    public JWTIdGenerator(Key secret){
        this.secret = secret;
    }

    @Override
    public String createId(Action action, Map<String, Object> state) {
        String username;
        //if username/id is already used, set UUID and the client can try a new login with an other username
        if(action.getPayload().containsKey("user") && !state.containsKey(action.getPayload().get("user").toString())){
            username = action.getPayload().get("user").toString();
        }
        else{
            username = UUID.randomUUID().toString();
        }
        return Jwts.builder().setSubject(username).
                //setExpiration(new Date(System.currentTimeMillis() + this.valid10Minutes)).
                signWith(this.secret).
                compact();
    }

    @Override
    public boolean validateId(String id, Action action, Map<String, Object> state) {
        boolean result = false;
        try{
            result = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(id).getBody().getSubject().length() > 0;
        }
        catch(Exception e){
            //is thrown if can't be parsed with the secret
        }
        return result;
    }
}
