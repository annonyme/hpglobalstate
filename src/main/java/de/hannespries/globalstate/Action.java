package de.hannespries.globalstate;

import lombok.Data;

import java.util.Map;

@Data
public class Action {
    private String token;
    private String action;
    private Map<String, Object> payload;
}
