package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateBetween extends FilterOperator {
    private Date start = new Date();
    private Date end = new Date();

    public DateBetween(Date start, Date end){
        if(start.getTime() < end.getTime()){
            this.start = start;
            this.end = end;
        }
        else{
            this.start = end;
            this.end = start;
        }
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
                result = this.start.getTime() >= timeStamp && this.end.getTime() >= timeStamp;
            }
        }
        catch(Exception e){

        }
        return result;
    }
}
