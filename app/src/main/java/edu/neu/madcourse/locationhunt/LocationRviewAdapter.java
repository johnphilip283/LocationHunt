package edu.neu.madcourse.locationhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.locationhunt.models.HuntLocation;

public class LocationRviewAdapter extends RecyclerView.Adapter<LocationRviewHolder> {

    private final List<HuntLocation> locations;
    private final Context ctx;
    private LocationClickListener listener;

    public LocationRviewAdapter(List<HuntLocation> locations, Context ctx) {
        this.locations = locations;
        this.ctx = ctx;
    }

    public void setOnLinkClickListener(LocationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationRviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
        return new LocationRviewHolder(view, listener, ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRviewHolder holder, int position) {
        HuntLocation destinationLocation = locations.get(position);
        holder.setHuntLocation(destinationLocation);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
