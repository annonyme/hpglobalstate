package de.hannespries.globalstate.defaults;

import de.hannespries.globalstate.Action;
import de.hannespries.globalstate.FacetFilter;
import de.hannespries.globalstate.StateQuery;

import java.util.HashMap;
import java.util.Map;

public class DefaultFacetFilterByToken implements FacetFilter {
    public Map<String, Object> filter(Action action, Map<String, Object> state) {
        Map<String, Object> result = new HashMap<String, Object>();
        return StateQuery.filterById(action.getToken(), state);
    }
}
