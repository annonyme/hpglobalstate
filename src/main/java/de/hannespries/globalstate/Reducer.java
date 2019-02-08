package de.hannespries.globalstate;

import javax.validation.constraints.NotNull;
import java.util.Map;

public interface Reducer {
    public String getAction();
    public boolean reduce(@NotNull Action action, Map<String, Object> state);
}
