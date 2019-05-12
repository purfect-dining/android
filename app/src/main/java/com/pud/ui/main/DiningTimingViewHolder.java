package com.pud.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pud.R;
import com.pud.model.DiningTiming;

import java.util.Date;

public class DiningTimingViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mType;
    private TextView mStatus;
    private TextView mRating;

    public DiningTimingViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.diningtiming_place_name);
        mType = itemView.findViewById(R.id.diningtiming_type);
        mStatus = itemView.findViewById(R.id.diningtiming_status);
        mRating = itemView.findViewById(R.id.diningtiming_rating);
    }

    public void bind(DiningTiming diningTiming, Double rating) {
        mTitle.setText(diningTiming.getOfPlace().getName());
        mType.setText(diningTiming.getDiningType().getName());

        Date date = new Date();
        long diff = diningTiming.getTo().getTime() - date.getTime();
        mStatus.setText("Closes in " + msToString(diff));
        mRating.setText("" + rating);
//        mTitle.setText(diningTiming.getFrom().toString().substring(0, 10) + "\n" + diningTiming.getOfPlace().getName() + " - " + diningTiming.getDiningType().getName() +
//                ":  " + diningTiming.getFrom().toString().substring(11, 16) + " | " +
//                diningTiming.getTo().toString().substring(11, 16) + "\n");
    }

    public String msToString(long ms) {
        long totalSecs = ms / 1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        String minsString = (mins == 0)
                ? "00"
                : ((mins < 10)
                ? "0" + mins
                : "" + mins);
        if (hours > 0)
            return hours + "h " + minsString + "m";
        else return mins + "m";
    }
}
