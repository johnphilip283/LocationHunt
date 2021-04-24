package edu.neu.madcourse.locationhunt.models;

public final class Constants {
    public static final double DEFAULT_CURRENT_LAT = 42.33035768454704;
    public static final double DEFAULT_CURRENT_LNG = -71.09758758572562;

    // average person walks at 3.5 miles per hour, which is this in seconds per meter
    public static final double AVG_WALKING_SPEED_SEC_PER_METER = 0.63912465487;

    // this many miles are in 1 meter.
    public static final double MILES_PER_METER = 0.00621371;

    public static final double SCORE_DEPRECIATION_FACTOR = 0.90;

    public static final int INITIAL_POINTS_PER_MILE = 100;

    public static final double SCORE_APPRECIATION_FACTOR = 1.05;

    public static final int MINUTES_PER_DEPRECIATION = 5;

}