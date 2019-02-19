package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

import java.util.Date;
import java.util.GregorianCalendar;

public class NewerThan extends FilterOperator {
    private Date value = new Date();

    public NewerThan(Date value){
        this.value = value;
    }

    @Override
    public boolean check(Object toCheckValue) {
        boolean result = false;
        try{
            long timeStamp = -1;
            if(toCheckValue instanceof Date){
                timeStamp = ((Date) toCheckValue).getTime();
            }
            else if(toCheckValue instanceof GregorianCalendar){
                timeStamp = ((GregorianCalendar) toCheckValue).getTimeInMillis();
            }

            if(timeStamp > 0){
                result = this.value.getTime() < timeStamp;
            }
        }
        catch(Exception e){

        }
        return result;
    }
}
