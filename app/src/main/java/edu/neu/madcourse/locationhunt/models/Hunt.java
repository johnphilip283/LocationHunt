package edu.neu.madcourse.locationhunt.models;

import androidx.annotation.NonNull;

public class Hunt {

    public long startTimestamp;
    public long duration;
    public HuntLocation destination;
    public double score;

    public Hunt() {

    }

    public long getDuration() {
        return duration;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public HuntLocation getDestination() {
        return destination;
    }

    public void setDestination(HuntLocation destination) {
        this.destination = destination;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public double getScore() { return score; }

    public void setScore(double score) { this.score = score; }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("timestamp: " + startTimestamp);
        sb.append("duration: " + duration);
        sb.append("hunt location: " + destination.toString());

        return sb.toString();
    }
}
