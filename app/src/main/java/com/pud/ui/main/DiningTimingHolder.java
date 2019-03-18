package com.pud.ui.main;

import android.view.View;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.DiningTiming;

import androidx.recyclerview.widget.RecyclerView;

public class DiningTimingHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;

    public DiningTimingHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item);
    }

    public void bind(DiningTiming diningTiming) {
        mTitle.setText(diningTiming.getFrom().toString().substring(0, 10) + "\n" + diningTiming.getOfPlace().getName() + " - " + diningTiming.getDiningType().getName() +
                ":  " + diningTiming.getFrom().toString().substring(11, 16) + " | " +
                diningTiming.getTo().toString().substring(11, 16) + "\n");
    }
}
