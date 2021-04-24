package edu.neu.madcourse.locationhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.neu.madcourse.locationhunt.models.Hunt;
import edu.neu.madcourse.locationhunt.models.HuntLocation;

public class HuntRviewAdapter extends RecyclerView.Adapter<HuntRviewHolder> {

    private final List<Hunt> hunts;
    private final Context ctx;

    public HuntRviewAdapter(List<Hunt> hunts, Context ctx) {
        this.hunts = hunts;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public HuntRviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hunt_card, parent, false);
        return new HuntRviewHolder(view, ctx);
    }

    @Override
    public void onBindViewHolder(@NonNull HuntRviewHolder holder, int position) {
        Hunt prevHunt = hunts.get(position);
        HuntLocation destination = prevHunt.getDestination();

        SimpleDateFormat sdfr = new SimpleDateFormat("MMM dd, yyyy");

        Date date = new Date((long) prevHunt.getStartTimestamp() * 1000);

        String durationInMin = (int) prevHunt.getDuration() / 60 + "";

        holder.destinationName.setText(ctx.getString(R.string.info_display, "Destination Name", destination.getName()));
        holder.duration.setText(ctx.getString(R.string.info_display, "Time elapsed",  durationInMin + " minutes"));
        holder.startDate.setText(ctx.getString(R.string.info_display, "Date", sdfr.format(date)));
        holder.score.setText(ctx.getString(R.string.info_display, "Score", prevHunt.getScore() + ""));
    }

    @Override
    public int getItemCount() {
        return hunts.size();
    }
}
