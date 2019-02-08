package de.hannespries.globalstate;

import javax.validation.constraints.NotNull;
import java.util.Map;

public interface IdGenerator {
    public String createId(@NotNull Action action, Map<String, Object> state);
    public boolean validateId(String id, @NotNull Action action, Map<String, Object> state);
}
