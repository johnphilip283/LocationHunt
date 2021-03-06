package edu.neu.madcourse.locationhunt;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.locationhunt.models.HuntLocation;

import static edu.neu.madcourse.locationhunt.models.Constants.MILES_PER_METER;

public class LocationRviewHolder extends RecyclerView.ViewHolder {

    public TextView distance;
    public TextView hint;
    private HuntLocation location;
    private Context ctx;

    public LocationRviewHolder(@NonNull View itemView, LocationClickListener listener, Context ctx) {
        super(itemView);
        distance = itemView.findViewById(R.id.location_card_distance);
        hint = itemView.findViewById(R.id.location_card_hint);
        this.ctx = ctx;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onLocationClick(location);
                    }
                }
            }
        });
    }

    public void setHuntLocation(HuntLocation location) {
        this.location = location;

        LocationService locationService = new LocationService(ctx);
        Location currentLocation = locationService.getCurrentLocation();
        
        // distance in meters -> need to convert to miles within 1 decimal place
        double distanceInMeters = 0;
        if (currentLocation != null) {
            distanceInMeters = currentLocation.distanceTo(location.retrieveLocation());
        }
        float distanceInMiles = (float) Math.round(MILES_PER_METER * distanceInMeters) / 10;

        this.hint.setText(ctx.getString(R.string.location_card_hint, location.getHint()));
        this.distance.setText(ctx.getString(R.string.location_card_distance, distanceInMiles));
    }

}
