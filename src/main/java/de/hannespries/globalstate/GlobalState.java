package de.hannespries.globalstate;

import de.hannespries.globalstate.defaults.DefaultIdGenerator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class GlobalState {
    private String idField = "__id";
    private List<ActionDispatcher> dispatchers = new ArrayList<>();
    private List<Reducer> reducers= new ArrayList<>();
    private List<FacetFilter> facetFilters = new ArrayList<>();
    private List<StateChangeListener> changeListeners = new ArrayList<>();
    private IdGenerator idGenerator = new DefaultIdGenerator();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Object> state = new ConcurrentHashMap<>(); //TODO make it thread-save: https://stackoverflow.com/questions/40531708/multithread-foreach-hashmap-loop

    public  Map<String, Object> action(Action action){
        if(this.idGenerator != null && (action.getToken() == null || action.getToken().length() == 0)){
            action.setToken(this.idGenerator.createId(action, this.state));
        }

        //TODO move idgeneration to one predefined dispatcher, added by constructor
        Map<String, Object> stateUnmod = Collections.unmodifiableMap(this.state);
        for (ActionDispatcher dispatcher: this.dispatchers) {
            dispatcher.dispatch(action, stateUnmod); //a dispatcher can't modify the state
        }

        Map<String, Object> filtered = new HashMap<>();
        if(this.idGenerator == null || this.idGenerator.validateId(action.getToken(), action, this.state)){
            boolean someThingChanged = false;
            //reducers to manipulate the state
            for (Reducer reducer: this.reducers) {
                if(reducer.getAction().equals(action.getAction()) && reducer.reduce(action, this.state)){
                    someThingChanged = true;
                }
            }

            //change listeners if the state was changed
            if(someThingChanged){
                for(StateChangeListener listener: this.changeListeners){
                    listener.onChange(action, this.state);
                }
            }

            //use facets to implement client-related visibility-restrictions on the whole state
            //without manipulating the state
            filtered = this.state;
            for(FacetFilter filter: this.facetFilters){
                filtered = filter.filter(action, filtered);
            }
        }

        return filtered;
    }
}
