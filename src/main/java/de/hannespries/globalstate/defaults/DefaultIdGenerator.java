package de.hannespries.globalstate.defaults;

import de.hannespries.globalstate.Action;
import de.hannespries.globalstate.IdGenerator;

import java.util.Map;
import java.util.UUID;

public class DefaultIdGenerator implements IdGenerator {
    public String createId(Action action, Map<String, Object> state) {
        return UUID.randomUUID().toString();
    }

    public boolean validateId(String id, Action action, Map<String, Object> state) {
        return id != null && id.length() > 0;
    }
}
