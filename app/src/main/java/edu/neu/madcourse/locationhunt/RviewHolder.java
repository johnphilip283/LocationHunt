package edu.neu.madcourse.locationhunt;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.locationhunt.models.Constants;
import edu.neu.madcourse.locationhunt.models.HuntLocation;

public class RviewHolder extends RecyclerView.ViewHolder {

    public TextView distance;
    public TextView hint;
    private HuntLocation location;
    private Context ctx;

    public RviewHolder(@NonNull View itemView, LocationClickListener listener, Context ctx) {
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

        currentLocation.setLatitude(Constants.DEFAULT_CURRENT_LAT);
        currentLocation.setLongitude(Constants.DEFAULT_CURRENT_LNG);


        // distance in meters -> need to convert to miles within 1 decimal place
        double distanceInMeters = currentLocation.distanceTo(location.getLocation());
        float distanceInMiles = (float) Math.round(0.00621371 * distanceInMeters) / 10;

        this.hint.setText(ctx.getString(R.string.location_card_hint, location.getHint()));
        this.distance.setText(ctx.getString(R.string.location_card_distance, distanceInMiles));
    }

}
