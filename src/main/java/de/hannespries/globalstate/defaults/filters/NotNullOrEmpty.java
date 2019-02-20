package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

public class NotNullOrEmpty extends FilterOperator {

    private boolean alsoEmpty = true;

    public NotNullOrEmpty(){

    }

    public NotNullOrEmpty(boolean alsoEmpty){
        this.alsoEmpty = alsoEmpty;
    }

    @Override
    public boolean check(Object toCheckValue) {
        return toCheckValue != null && (this.alsoEmpty && !toCheckValue.toString().isEmpty());
    }
}
