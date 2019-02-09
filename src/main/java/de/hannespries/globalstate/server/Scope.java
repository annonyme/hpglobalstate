package de.hannespries.globalstate.server;

import de.hannespries.globalstate.GlobalState;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class Scope {
    public static final GlobalState state = new GlobalState();
    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
}
