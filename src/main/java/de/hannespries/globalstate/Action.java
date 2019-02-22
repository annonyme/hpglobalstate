package de.hannespries.globalstate;

import lombok.Data;

import java.util.Map;

@Data
public class Action {
    private String token;
    private String action;
    private Map<String, Object> payload;

    public Action(){

    }

    public Action(String action){
        this.action = action;
    }

    public Action(String token, String action){
        this.token = token;
        this.action = action;
    }
}
