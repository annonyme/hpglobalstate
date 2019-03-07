package de.hannespries.globalstate;

import de.hannespries.globalstate.utils.ObjectPathReader;
import de.hannespries.globalstate.utils.TestValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestObjects {
    @Test
    public void readSimpleMap() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("test", "blubb");
        String result = ObjectPathReader.read(map, "test").toString();
        Assert.assertEquals("blubb", result);
    }

    @Test
    public void readComplexMap() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("test", "blubb");

        Map<String, Object> parent = new HashMap<>();
        parent.put("subtest", map);

        String result = ObjectPathReader.read(parent, "subtest.test").toString();
        Assert.assertEquals("blubb", result);
    }

    @Test
    public void readSimpleObject() throws Exception{
        TestValue tv = new TestValue();
        tv.setValue("blubb");

        String result = ObjectPathReader.read(tv, "value").toString();
        Assert.assertEquals("blubb", result);
    }

    @Test
    public void readComplexObject() throws Exception {
        TestValue tv = new TestValue();
        tv.setValue("blubb");

        Map<String, Object> parent = new HashMap<>();
        parent.put("subtest", tv);

        String result = ObjectPathReader.read(parent, "subtest.value").toString();
        Assert.assertEquals("blubb", result);
    }

    @Test
    public void readComplexObjectGetter() throws Exception {
        TestValue tv = new TestValue();
        tv.setValue("blubb");

        Map<String, Object> parent = new HashMap<>();
        parent.put("subtest", tv);

        String result = ObjectPathReader.read(parent, "subtest.getValue()").toString();
        Assert.assertEquals("blubb", result);
    }
}
