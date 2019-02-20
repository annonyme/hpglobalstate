package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

public class IsNullOrEmpty extends FilterOperator {
    private boolean alsoEmpty = true;

    public IsNullOrEmpty(){

    }

    public IsNullOrEmpty(boolean alsoEmpty){
        this.alsoEmpty = alsoEmpty;
    }

    @Override
    public boolean check(Object toCheckValue) {
        return toCheckValue == null || (this.alsoEmpty && toCheckValue.toString().isEmpty());
    }
}
