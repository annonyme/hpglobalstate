package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

/**
 * the value has to be a map with the fields longitude and latitude
 */
public class CoordsDistance extends FilterOperator {
    private int maxDistance = 1;
    private double longitude = 0d;
    private double latitude = 0d;

    /**
     * check for max distance to a given coord
     * @param maxDistanceMeter full meters
     * @param longitude coords
     * @param latitude coords
     */
    public CoordsDistance(int maxDistanceMeter, double longitude, double latitude){
        this.maxDistance = maxDistanceMeter;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public boolean check(Object toCheckValue) {
        boolean result = false;
        if(toCheckValue instanceof CoordsDistanceInterface){
            try{
                CoordsDistanceInterface obj = (CoordsDistanceInterface) toCheckValue;
                double cLong = obj.getLong();
                double cLat = obj.getLat();

                double theta = this.longitude - cLong;
                double dist = Math.sin(Math.toRadians(this.latitude)) * Math.sin(Math.toRadians(cLat)) *
                        Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(cLat)) *
                        Math.cos(Math.toRadians(theta));

                dist = Math.acos(dist);
                dist = Math.toDegrees(dist);
                double distKM = dist * 60 * 1.1515 * 1.609344;
                result = this.maxDistance <= Math.abs(distKM * 1000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
