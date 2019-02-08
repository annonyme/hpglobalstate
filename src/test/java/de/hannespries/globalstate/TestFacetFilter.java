package de.hannespries.globalstate;

import de.hannespries.globalstate.defaults.DefaultFacetFilterByToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestFacetFilter {
    @Test
    public void filter(){
        Action action = new Action();

        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();
        Map<String, Object> artefact2 = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);
        artefact2.put("__id", id + "xxx");

        StateQuery.merge(id, artefact, state);
        StateQuery.merge(id + "xxx", artefact2, state);

        action.setToken(id + "xxx");

        DefaultFacetFilterByToken filter = new DefaultFacetFilterByToken();

        Map<String, Object> result = filter.filter(action, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertFalse(result.containsKey(id));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(artefact2, result.get(id + "xxx"));
    }
}
