package edu.neu.madcourse.locationhunt;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HuntRviewHolder extends RecyclerView.ViewHolder {

    public TextView destinationName;
    public TextView duration;
    public TextView startDate;
    public TextView score;

    public HuntRviewHolder(@NonNull View itemView,  Context ctx) {
        super(itemView);
        destinationName = itemView.findViewById(R.id.hunt_card_destination_name);
        duration = itemView.findViewById(R.id.hunt_card_duration);
        startDate = itemView.findViewById(R.id.hunt_card_start_date);
        score = itemView.findViewById(R.id.hunt_card_score);
    }


}
