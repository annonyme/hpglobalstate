package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

public class LesserThan extends FilterOperator {
    private float value = 0f;

    public LesserThan(float value){
        this.value = value;
    }

    @Override
    public boolean check(Object toCheckValue) {
        boolean result = false;
        try{
            result = Float.parseFloat(toCheckValue.toString()) < this.value;
        }
        catch(Exception e){

        }
        return result;
    }
}
