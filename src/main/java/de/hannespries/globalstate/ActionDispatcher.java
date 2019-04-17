package de.hannespries.globalstate;

import java.util.Map;

public interface ActionDispatcher {
    public void dispatch(Action action, Map<String, Object> state);
}
