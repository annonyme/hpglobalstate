package de.hannespries.globalstate;

import java.util.HashMap;
import java.util.Map;

public class TestReducer implements Reducer{
    @Override
    public String getAction() {
        return "test";
    }

    @Override
    public boolean reduce(Action action, Map<String, Object> state) {
        Map<String, Object> artifact = new HashMap<>();
        artifact.put("value", action.getPayload().get("test"));
        state.put(action.getToken(), artifact);
        return true;
    }
}
