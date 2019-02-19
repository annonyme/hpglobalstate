package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

public class MinMaxStringLen extends FilterOperator {
    private int min = 0;
    private int max = -1;

    public MinMaxStringLen(int min, int max){
        this.min = min;
        this.max = max;
    }
    public MinMaxStringLen(int min){
        this.min = min;
    }

    @Override
    public boolean check(Object toCheckValue) {
        return toCheckValue.toString().length() >= min && (max < 0 || toCheckValue.toString().length() <=max);
    }
}
