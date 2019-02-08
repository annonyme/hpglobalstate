package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

public class FieldExists extends FilterOperator {
    @Override
    public boolean check(Object toCheckValue) {
        return true;
    }
}
