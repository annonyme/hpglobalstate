package de.hannespries.globalstate;

import de.hannespries.globalstate.defaults.JWTIdGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.Assert;
import org.junit.Test;

import java.security.Key;
import java.util.HashMap;

public class TestIdGen {

    @Test
    public void createJWT(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        JWTIdGenerator gen = new JWTIdGenerator(key);

        Action action = new Action();
        action.setPayload(new HashMap<>());
        action.getPayload().put("user", "test");

        String id = gen.createId(action, new HashMap<>());

        Assert.assertTrue(id.length() > 0);
    }

    @Test
    public void validateJWT(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        JWTIdGenerator gen = new JWTIdGenerator(key);

        Action action = new Action();
        action.setPayload(new HashMap<>());
        action.getPayload().put("user", "test");

        String id = gen.createId(action, new HashMap<>());

        Assert.assertTrue(gen.validateId(id, action, new HashMap<>()));
        Assert.assertEquals(action.getPayload().get("user").toString(), Jwts.parser().setSigningKey(key).parseClaimsJws(id).getBody().getSubject());
    }

    @Test
    public void validateJWTFailing(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        JWTIdGenerator gen = new JWTIdGenerator(key);

        Key key2 = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        JWTIdGenerator gen2 = new JWTIdGenerator(key2);

        Action action = new Action();
        action.setPayload(new HashMap<>());
        action.getPayload().put("user", "test");

        String id = gen.createId(action, new HashMap<>());

        Assert.assertFalse(gen2.validateId(id, action, new HashMap<>()));
    }
}
