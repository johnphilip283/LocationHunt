package edu.neu.madcourse.locationhunt.models;

import android.location.Location;

public class Hunt {

    public long startTimestamp;
    public long duration;
    public String destination;
    public Location destinationLocation;

    public Hunt() {

    }

    public long getDuration() {
        return duration;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public String getDestination() {
        return destination;
    }

    public Location getDestinationLocation() { return destinationLocation; }

    public void setDestinationLocation(Location destinationLocation) { this.destinationLocation = destinationLocation; }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
}
