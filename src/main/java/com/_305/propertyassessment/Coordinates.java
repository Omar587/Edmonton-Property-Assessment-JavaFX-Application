package com._305.propertyassessment;

import java.util.Objects;

public class Coordinates {

    private double latitude;
    private double longitude;



    public Coordinates(double latitude, double longitude) {

        if (longitude < -180 || longitude > 80){
            throw new IllegalArgumentException("Longitude must range from -180 to 80.");
        }


        if (latitude < -90 || latitude > 90){
            throw new IllegalArgumentException("Latitude must range from -90 to 90.");
        }

        this.latitude = latitude;
        this.longitude = longitude;

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90){
            throw new IllegalArgumentException("Latitude must range from -90 to 90.");
        }

        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {

        if (longitude < -180 || longitude > 80){
            throw new IllegalArgumentException("Longitude must range from -180 to 80.");
        }
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {

        return  "(" + latitude + ", " + longitude+ ")";
    }
}
