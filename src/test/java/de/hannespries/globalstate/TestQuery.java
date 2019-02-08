package de.hannespries.globalstate;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestQuery {
    @Test
    public void exists(){
        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);
        state.put(id, artefact);

        Assert.assertTrue(StateQuery.exists(id, state));
    }

    @Test
    public void findById(){
        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);
        state.put(id, artefact);

        Map<String, Object> filtered = StateQuery.filterById(id, state);

        Assert.assertTrue(filtered.containsKey(id));
        Assert.assertEquals(artefact, filtered.get(id));
    }

    @Test
    public void mergeAdd(){
        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);

        StateQuery.merge(id, artefact, state);

        Assert.assertTrue(state.containsKey(id));
        Assert.assertEquals(artefact, state.get(id));
    }

    @Test
    public void mergeUpdate(){
        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();
        Map<String, Object> artefact2 = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);
        artefact2.put("__id", id + "xxx");

        StateQuery.merge(id, artefact, state);

        Assert.assertTrue(state.containsKey(id));
        Assert.assertEquals(artefact, state.get(id));

        StateQuery.merge(id, artefact2, state);

        Assert.assertTrue(state.containsKey(id));
        Assert.assertEquals(1, state.size());
        Assert.assertEquals(artefact2, state.get(id));
    }

    @Test
    public void filterSingle(){
        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();
        Map<String, Object> artefact2 = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);
        artefact2.put("__id", id + "xxx");

        StateQuery.merge(id, artefact, state);
        StateQuery.merge(id + "xxx", artefact2, state);

        Map<String, List<Object>> filter = new HashMap<String, List<Object>>();
        List<Object> values = new ArrayList<Object>();
        values.add(id + "xxx");
        filter.put("__id", values);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(artefact2, result.get(id + "xxx"));
    }

    @Test
    public void filterBoth(){
        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();
        Map<String, Object> artefact2 = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);
        artefact2.put("__id", id + "xxx");

        StateQuery.merge(id, artefact, state);
        StateQuery.merge(id + "xxx", artefact2, state);

        Map<String, List<Object>> filter = new HashMap<String, List<Object>>();
        List<Object> values = new ArrayList<Object>();
        values.add(id);
        values.add(id + "xxx");
        filter.put("__id", values);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertTrue(result.containsKey(id));
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(artefact2, result.get(id + "xxx"));
    }

    @Test
    public void delete(){
        Map<String, Object> state = new HashMap<String, Object>();
        Map<String, Object> artefact = new HashMap<String, Object>();
        Map<String, Object> artefact2 = new HashMap<String, Object>();

        String id = UUID.randomUUID().toString();
        artefact.put("__id", id);
        artefact2.put("__id", id + "xxx");

        StateQuery.merge(id, artefact, state);
        StateQuery.merge(id + "xxx", artefact2, state);

        Map<String, List<Object>> filter = new HashMap<String, List<Object>>();
        List<Object> values = new ArrayList<Object>();
        values.add(id);
        values.add(id + "xxx");
        filter.put("__id", values);

        StateQuery.delete(id, state);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertFalse(result.containsKey(id));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(artefact2, result.get(id + "xxx"));
    }
}
