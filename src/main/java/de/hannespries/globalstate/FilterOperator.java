package de.hannespries.globalstate;

import lombok.Data;

@Data
public abstract class FilterOperator {
    private Object value;
    public abstract boolean check(Object toCheckValue);
}
