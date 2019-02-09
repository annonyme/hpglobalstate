package de.hannespries.globalstate.server;

import de.hannespries.globalstate.FacetFilter;
import de.hannespries.globalstate.IdGenerator;
import de.hannespries.globalstate.Reducer;
import de.hannespries.globalstate.StateChangeListener;
import org.apache.meecrowave.Meecrowave;

import java.util.List;

public class GlobalStateServer {
    public GlobalStateServer start(int port){
        Meecrowave.Builder builder = new Meecrowave.Builder();
        builder.setHttpPort(port);
        try (Meecrowave meecrowave = new Meecrowave(builder)) {
            meecrowave.bake().await();
        }
        return this;
    }

    public GlobalStateServer start(){
        return this.start(8082);
    }

    public GlobalStateServer setIdGenerator(IdGenerator gen){
        Scope.state.setIdGenerator(gen);
        return this;
    }

    public GlobalStateServer setReducers(List<Reducer> reducers){
        Scope.state.setReducers(reducers);
        return this;
    }

    public GlobalStateServer setFacetFilters(List<FacetFilter> filters){
        Scope.state.setFacetFilters(filters);
        return this;
    }

    public GlobalStateServer setChangeListeners(List<StateChangeListener> listeners){
        Scope.state.setChangeListeners(listeners);
        return this;
    }

    public GlobalStateServer setIdFieldName(String name){
        Scope.state.setIdField(name);
        return this;
    }
}
