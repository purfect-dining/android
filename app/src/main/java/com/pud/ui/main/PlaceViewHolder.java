package com.pud.ui.main;

import android.view.View;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.Place;

import androidx.recyclerview.widget.RecyclerView;

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mStatus;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.diningtiming_place_name);
        mStatus = itemView.findViewById(R.id.diningtiming_status);
    }

    public void bind(Place place) {
        mTitle.setText(place.getName());
        if (place.getName().startsWith("H")) mStatus.setText("Opens Tomorrow");
        else mStatus.setText("Closed");
    }
}
