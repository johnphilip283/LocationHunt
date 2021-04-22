package edu.neu.madcourse.locationhunt;

public class LocationCard {

    public String hint;
    public String distance;

    public LocationCard(String hint, String distance) {
        this.hint = hint;
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public String getHint() {
        return hint;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

}
