package de.hannespries.globalstate.defaults;

import de.hannespries.globalstate.Action;
import de.hannespries.globalstate.FacetFilter;
import de.hannespries.globalstate.StateQuery;

import java.util.Map;

public class DefaultFacetFilterByToken implements FacetFilter {
    public Map<String, Object> filter(Action action, Map<String, Object> state) {
        return StateQuery.filterById(action.getToken(), state);
    }
}
