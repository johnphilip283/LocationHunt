package edu.neu.madcourse.locationhunt;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.locationhunt.models.HuntLocation;

public class RviewAdapter extends RecyclerView.Adapter<RviewHolder> {

    private final List<HuntLocation> locations;
    private final Context ctx;

    public RviewAdapter(List<HuntLocation> locations, Context ctx) {
        this.locations = locations;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public RviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
        return new RviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RviewHolder holder, int position) {
        HuntLocation destinationLocation = locations.get(position);

        LocationService locationService = new LocationService(ctx);
        Location currentLocation = locationService.getCurrentLocation();

        if (currentLocation == null) {
            Log.e("RviewAdapter", "Current location is null");
        }

        // distance in meters -> need to convert to miles within 1 decimal place
        double distanceInMeters = currentLocation.distanceTo(destinationLocation.getLocation());
        float distanceInMiles = (float) Math.round(0.00621371 * distanceInMeters) / 10;

        holder.hint.setText(ctx.getString(R.string.location_card_hint, destinationLocation.getHint()));
        holder.distance.setText(ctx.getString(R.string.location_card_distance, distanceInMiles));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
