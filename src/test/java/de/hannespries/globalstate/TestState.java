package de.hannespries.globalstate;

import de.hannespries.globalstate.defaults.DefaultFacetFilterByToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestState {

    @Test
    public void sendAction(){
        GlobalState state = new GlobalState();
        state.getReducers().add(new TestReducer());

        Action action = new Action("test");
        action.setPayload(new HashMap<>());
        long time = System.currentTimeMillis();
        action.getPayload().put("test", time);
        Map<String, Object> result = state.action(action);

        Assert.assertTrue(result.containsKey(action.getToken()));
        Assert.assertTrue(result.get(action.getToken()) instanceof Map);
        if(result.get(action.getToken()) instanceof Map){
            Map artifact = (Map) result.get(action.getToken());
            Assert.assertEquals(artifact.get("value").toString(), Long.toString(time));
        }

    }

    @Test
    public void sendActionFacet(){
        GlobalState state = new GlobalState();
        state.getReducers().add(new TestReducer());
        state.getFacetFilters().add(new DefaultFacetFilterByToken(true));

        Action action = new Action("test");
        action.setPayload(new HashMap<>());
        long time = System.currentTimeMillis();
        action.getPayload().put("test", time);
        Map<String, Object> result = state.action(action);

        Assert.assertTrue(result.containsKey("value"));
        Assert.assertEquals(result.get("value").toString(), Long.toString(time));
    }
}
