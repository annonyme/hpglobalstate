package de.hannespries.globalstate.defaults;

import de.hannespries.globalstate.Action;
import de.hannespries.globalstate.FacetFilter;
import de.hannespries.globalstate.StateQuery;

import java.util.Map;

public class DefaultFacetFilterByToken implements FacetFilter {
    private boolean reduceId = false;

    public DefaultFacetFilterByToken(){

    }

    public DefaultFacetFilterByToken(boolean reduceId){
        this.reduceId = reduceId;
    }

    public Map<String, Object> filter(Action action, Map<String, Object> state) {
        Map<String, Object> result = StateQuery.filterById(action.getToken(), state);
        if(!reduceId){
            return result;
        }
        else if(result.containsKey(action.getToken()) && result.get(action.getToken()) instanceof Map){
            return (Map) result.get(action.getToken());
        }
        return null;
    }
}
