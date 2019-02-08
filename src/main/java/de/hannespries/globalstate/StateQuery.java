package de.hannespries.globalstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StateQuery {
    public static Map<String, Object> filter(Map<String, List<Object>> filtervalues, Map<String, Object> state){
        Map<String, Object> result = new HashMap<>();
        if(state instanceof ConcurrentHashMap){
            ConcurrentHashMap cState = (ConcurrentHashMap) state;

            cState.search(Runtime.getRuntime().availableProcessors() - 2, (key, item) -> {
                if (item instanceof Map) {
                    Map subMap = (Map) item;
                    for(String keyF: filtervalues.keySet()){
                        if(subMap.containsKey(keyF)){
                            //simple equals for objects
                            if(filtervalues.get(keyF).contains(subMap.get(keyF))){
                                result.put(key.toString(), subMap);
                            }
                            else{
                                //using complex filter logic with FilterOperators
                                for(Object value: filtervalues.get(keyF)){
                                    if(value instanceof FilterOperator){
                                        FilterOperator op = (FilterOperator) value;
                                        if(op.check(subMap.get(keyF))){
                                            result.put(key.toString(), subMap);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return null;
            });
        }
        else {
            for (String key : state.keySet()) {
                if (state.get(key) instanceof Map) {
                    Map subMap = (Map) state.get(key);
                    for(String keyF: filtervalues.keySet()){
                        if(subMap.containsKey(keyF)){
                            //simple equals for objects
                            if(filtervalues.get(keyF).contains(subMap.get(keyF))){
                                result.put(key, subMap);
                            }
                            else{
                                //using complex filter logic with FilterOperators
                                for(Object value: filtervalues.get(keyF)){
                                    if(value instanceof FilterOperator){
                                        FilterOperator op = (FilterOperator) value;
                                        if(op.check(subMap.get(keyF))){
                                            result.put(key, subMap);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static Map<String, Object> filterByIds(List<String> ids, Map<String, Object> state){
        Map<String, Object> result = new HashMap<>();
        for (String key : state.keySet()) {
            if (ids.contains(key)) {
                result.put(key, state.get(key));
            }
        }
        return result;
    }

    public static Map<String, Object> filterById(String id, Map<String, Object> state){
        List<String> ids = new ArrayList<>();
        ids.add(id);
        return filterByIds(ids, state);
    }

    public static void merge(String id, Map<String, Object> artefact, Map<String, Object> state){
        state.put(id, artefact);
    }

    public static void merge(Action action,  Map<String, Object> state){
        merge(action.getToken(), action.getPayload(), state);
    }

    public static boolean delete(String id, Map<String, Object> state){
        state.remove(id);
        return state.containsKey(id);
    }

    public static boolean delete(Action action, Map<String, Object> state){
        return delete(action.getToken(), state);
    }

    public static boolean exists(String id, Map<String, Object> state){
        return state.containsKey(id);
    }

    public static boolean exists(Action action, Map<String, Object> state){
        return exists(action.getToken(), state);
    }
}
