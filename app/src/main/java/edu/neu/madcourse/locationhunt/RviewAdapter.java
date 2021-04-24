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
    private LocationClickListener listener;

    public RviewAdapter(List<HuntLocation> locations, Context ctx) {
        this.locations = locations;
        this.ctx = ctx;
    }

    public void setOnLinkClickListener(LocationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
        return new RviewHolder(view, listener, ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull RviewHolder holder, int position) {
        HuntLocation destinationLocation = locations.get(position);
        holder.setHuntLocation(destinationLocation);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
