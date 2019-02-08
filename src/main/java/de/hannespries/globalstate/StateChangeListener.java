package de.hannespries.globalstate;

import java.util.Map;

public interface StateChangeListener {
    public void onChange(Action action, Map<String, Object> state);
}
