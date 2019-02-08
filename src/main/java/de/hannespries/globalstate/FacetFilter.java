package de.hannespries.globalstate;

import java.util.Map;

public interface FacetFilter {
    public Map<String, Object> filter(Action action, Map<String, Object> state);
}
