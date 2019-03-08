package de.hannespries.globalstate;

import de.hannespries.globalstate.defaults.filters.FieldExists;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TestQuery {
    @Test
    public void exists(){
        Map<String, Object> state = new HashMap<>();
        Map<String, Object> artifact = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id);
        state.put(id, artifact);

        Assert.assertTrue(StateQuery.exists(id, state));
    }

    @Test
    public void findById(){
        Map<String, Object> state = new HashMap<>();
        Map<String, Object> artifact = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id);
        state.put(id, artifact);

        Map<String, Object> filtered = StateQuery.filterById(id, state);

        Assert.assertTrue(filtered.containsKey(id));
        Assert.assertEquals(artifact, filtered.get(id));
    }

    @Test
    public void mergeAdd(){
        Map<String, Object> state = new HashMap<>();
        Map<String, Object> artifact = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id);

        StateQuery.merge(id, artifact, state);

        Assert.assertTrue(state.containsKey(id));
        Assert.assertEquals(artifact, state.get(id));
    }

    @Test
    public void mergeUpdate(){
        Map<String, Object> state = new HashMap<>();
        Map<String, Object> artifact = new HashMap<>();
        Map<String, Object> artifact2 = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id);
        artifact2.put("__id", id + "xxx");

        StateQuery.merge(id, artifact, state);

        Assert.assertTrue(state.containsKey(id));
        Assert.assertEquals(artifact, state.get(id));

        StateQuery.merge(id, artifact2, state);

        Assert.assertTrue(state.containsKey(id));
        Assert.assertEquals(1, state.size());
        Assert.assertEquals(artifact2, state.get(id));
    }

    @Test
    public void filterSingle(){
        Map<String, Object> state = new HashMap<>();
        Map<String, Object> artifact = new HashMap<>();
        Map<String, Object> artifact2 = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id);
        artifact2.put("__id", id + "xxx");

        StateQuery.merge(id, artifact, state);
        StateQuery.merge(id + "xxx", artifact2, state);

        Map<String, List<Object>> filter = new HashMap<>();
        List<Object> values = new ArrayList<>();
        values.add(id + "xxx");
        filter.put("__id", values);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(artifact2, result.get(id + "xxx"));
    }

    @Test
    public void filterSingle2(){
        Map<String, Object> state = new ConcurrentHashMap<>();
        Map<String, Object> artifact = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id + "xxx");

        StateQuery.merge(id + "xxx", artifact, state);

        for(int i = 0; i < 2500; i++){
            Map<String, Object> art = new HashMap<>();
            String artId = "zzz" + i;
            art.put("__id", artId);
            StateQuery.merge(artId, art, state);
        }

        Map<String, List<Object>> filter = new HashMap<>();
        List<Object> values = new ArrayList<>();
        values.add(id + "xxx");
        filter.put("__id", values);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(artifact, result.get(id + "xxx"));
    }

    @Test
    public void filterSingleFieldExists(){
        Map<String, Object> state = new ConcurrentHashMap<>();
        Map<String, Object> artifact = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id + "xxx");
        artifact.put("fieldX", "X");

        StateQuery.merge(id + "xxx", artifact, state);

        for(int i = 0; i < 2500; i++){
            Map<String, Object> art = new HashMap<>();
            String artId = "zzz" + i;
            art.put("__id", artId);
            StateQuery.merge(artId, art, state);
        }

        Map<String, List<Object>> filter = new HashMap<>();
        List<Object> values = new ArrayList<>();
        values.add(new FieldExists());
        filter.put("fieldX", values);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(artifact, result.get(id + "xxx"));
    }

    @Test
    public void filterBoth(){
        Map<String, Object> state = new HashMap<>();
        Map<String, Object> artifact = new HashMap<>();
        Map<String, Object> artifact2 = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id);
        artifact2.put("__id", id + "xxx");

        StateQuery.merge(id, artifact, state);
        StateQuery.merge(id + "xxx", artifact2, state);

        Map<String, List<Object>> filter = new HashMap<>();
        List<Object> values = new ArrayList<>();
        values.add(id);
        values.add(id + "xxx");
        filter.put("__id", values);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertTrue(result.containsKey(id));
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(artifact2, result.get(id + "xxx"));
    }

    @Test
    public void delete(){
        Map<String, Object> state = new HashMap<>();
        Map<String, Object> artifact = new HashMap<>();
        Map<String, Object> artifact2 = new HashMap<>();

        String id = UUID.randomUUID().toString();
        artifact.put("__id", id);
        artifact2.put("__id", id + "xxx");

        StateQuery.merge(id, artifact, state);
        StateQuery.merge(id + "xxx", artifact2, state);

        Map<String, List<Object>> filter = new HashMap<>();
        List<Object> values = new ArrayList<>();
        values.add(id);
        values.add(id + "xxx");
        filter.put("__id", values);

        StateQuery.delete(id, state);

        Map<String, Object> result = StateQuery.filter(filter, state);

        Assert.assertTrue(result.containsKey(id + "xxx"));
        Assert.assertFalse(result.containsKey(id));
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(artifact2, result.get(id + "xxx"));
    }
}
