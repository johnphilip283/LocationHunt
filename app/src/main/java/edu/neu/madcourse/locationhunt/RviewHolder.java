package edu.neu.madcourse.locationhunt;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RviewHolder extends RecyclerView.ViewHolder {

    public TextView distance;
    public TextView hint;

    public RviewHolder(@NonNull View itemView) {
        super(itemView);
        distance = itemView.findViewById(R.id.location_card_distance);
        hint = itemView.findViewById(R.id.location_card_hint);
    }

}
