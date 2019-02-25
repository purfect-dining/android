package com.pud.ui.place;

import android.view.View;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.Comment;
import com.pud.model.Place;

import androidx.recyclerview.widget.RecyclerView;

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item);
    }

    public void bind(Comment place) {
        mTitle.setText(place.getText());
    }
}
