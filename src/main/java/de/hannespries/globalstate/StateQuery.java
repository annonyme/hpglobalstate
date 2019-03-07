package de.hannespries.globalstate;

import de.hannespries.globalstate.utils.ObjectPathReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateQuery {
    public static Map<String, Object> filter(Map<String, List<Object>> filtervalues, Map<String, Object> state){
        Map<String, Object> result = new HashMap<>();
        for(String keyF: filtervalues.keySet()){
            for (String key : state.keySet()) {
                try{
                    Object value = ObjectPathReader.read(state.get(key), keyF);
                    if(filtervalues.get(keyF).contains(value)){
                        result.put(key, state.get(key));
                    }
                    else{
                        for(Object checkValue: filtervalues.get(keyF)){
                            if(checkValue instanceof FilterOperator){
                                FilterOperator op = (FilterOperator) checkValue;
                                if(op.check(value)){
                                    result.put(key, state.get(key));
                                }
                            }
                        }
                    }
                }
                catch(Exception e){
                    //TODO
                }
            }
        }
        return result;
    }


//    public static Map<String, Object> filter(Map<String, List<Object>> filtervalues, Map<String, Object> state){
//        Map<String, Object> result = new HashMap<>();
//        Map<String, Float> relevances = new HashMap<>(); //key + 0-1 for relevance //TODO ????? or do it another way
//
//
//        //TODO
//        //loop through filtervalues.. get field-value by state 1st-level field to hold multithreading-logic
//        //if not ConcurrentHashMap.. use state as 1st level input for field resolving
//
//        if(state instanceof ConcurrentHashMap){
//            ConcurrentHashMap cState = (ConcurrentHashMap) state;
//
//            cState.search(Runtime.getRuntime().availableProcessors() - 2, (key, item) -> {
//                if (item instanceof Map) {
//                    Map subMap = (Map) item;
//                    for(String keyF: filtervalues.keySet()){
//                        if(subMap.containsKey(keyF)){
//                            //simple equals for objects
//                            if(filtervalues.get(keyF).contains(subMap.get(keyF))){
//                                result.put(key.toString(), subMap); //TODO ????? count relevance
//                            }
//                            else{
//                                //using complex filter logic with FilterOperators
//                                for(Object value: filtervalues.get(keyF)){
//                                    if(value instanceof FilterOperator){
//                                        FilterOperator op = (FilterOperator) value;
//                                        if(op.check(subMap.get(keyF))){
//                                            result.put(key.toString(), subMap); //TODO ????? count relevance
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                return null;
//            });
//        }
//        else {
//            for (String key : state.keySet()) {
//                if (state.get(key) instanceof Map) {
//                    Map subMap = (Map) state.get(key);
//                    for(String keyF: filtervalues.keySet()){
//                        if(subMap.containsKey(keyF)){
//                            //simple equals for objects
//                            if(filtervalues.get(keyF).contains(subMap.get(keyF))){
//                                result.put(key, subMap); //TODO ????? count relevance
//                            }
//                            else{
//                                //using complex filter logic with FilterOperators
//                                for(Object value: filtervalues.get(keyF)){
//                                    if(value instanceof FilterOperator){
//                                        FilterOperator op = (FilterOperator) value;
//                                        if(op.check(subMap.get(keyF))){
//                                            result.put(key, subMap); //TODO ????? count relevance
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        //TODO ????? compose the final list from relevances
//
//        return result;
//    }

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

    public static void merge(String id, Map<String, Object> artifact, Map<String, Object> state){
        state.put(id, artifact);
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
