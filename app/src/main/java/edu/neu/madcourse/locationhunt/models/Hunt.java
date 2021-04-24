package edu.neu.madcourse.locationhunt.models;

import androidx.annotation.NonNull;

import edu.neu.madcourse.locationhunt.LocationService;

import static edu.neu.madcourse.locationhunt.models.Constants.AVG_WALKING_SPEED_SEC_PER_METER;
import static edu.neu.madcourse.locationhunt.models.Constants.INITIAL_POINTS_PER_MILE;
import static edu.neu.madcourse.locationhunt.models.Constants.MILES_PER_METER;
import static edu.neu.madcourse.locationhunt.models.Constants.MINUTES_PER_DEPRECIATION;
import static edu.neu.madcourse.locationhunt.models.Constants.SCORE_APPRECIATION_FACTOR;
import static edu.neu.madcourse.locationhunt.models.Constants.SCORE_DEPRECIATION_FACTOR;

public class Hunt {

    public double startTimestamp;
    public double duration; // in seconds
    public HuntLocation destination;
    public double score;

    public Hunt() {

    }

    public Hunt(double startTimestamp, double duration, HuntLocation destination, double score) {
        this.destination = destination;
        this.startTimestamp = startTimestamp;
        this.duration = duration;
        this.score = score;
    }

    public Hunt(double startTimestamp, double duration, HuntLocation destination) {
        this.destination = destination;
        this.startTimestamp = startTimestamp;
        this.duration = duration;
        this.score = this.getScore();
    }


    public double getDuration() {
        return duration;
    }

    public double getStartTimestamp() {
        return startTimestamp;
    }

    public HuntLocation getDestination() {
        return destination;
    }

    public void setDestination(HuntLocation destination) {
        this.destination = destination;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public double getScore() { return calculateScore(this.destination, this.duration); }

    public static double calculateScore(HuntLocation destination, double duration) {

        float metersToDestination = LocationService.getDefaultLocation().distanceTo(destination.retrieveLocation());

        double initialScore = INITIAL_POINTS_PER_MILE * MILES_PER_METER * metersToDestination;
        double expectedSeconds = AVG_WALKING_SPEED_SEC_PER_METER * metersToDestination;

        double diffInSeconds = duration - expectedSeconds;

        if (diffInSeconds < 0) {
            // bonus for getting there before time.
            initialScore *= SCORE_APPRECIATION_FACTOR;
        }
        while (diffInSeconds > 0) {
            initialScore *= SCORE_DEPRECIATION_FACTOR;
            diffInSeconds -= MINUTES_PER_DEPRECIATION * 60;
        }

        return (int) initialScore;
    }

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
