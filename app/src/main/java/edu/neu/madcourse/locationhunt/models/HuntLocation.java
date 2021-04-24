package edu.neu.madcourse.locationhunt.models;

import android.location.Location;

import androidx.annotation.NonNull;

public class HuntLocation {

    public String hint;
    public double latitude;
    public double longitude;
    public String name;

    public HuntLocation() {

    }

    public HuntLocation(String hint, double latitude, double longitude, String name) {
        this.hint = hint;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public HuntLocation(String hint, Location location, String name) {
        this.name = name;
        this.hint = hint;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Location retrieveLocation() {
        Location location = new Location("");
        location.setLatitude(this.latitude);
        location.setLongitude(this.longitude);
        return location;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("hint: " + hint);
        sb.append("latitude: " + latitude);
        sb.append("longitude: " + longitude);
        sb.append("name: " + name);

        return sb.toString();
    }
}
