package com.pud.ui.user;

import android.view.View;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.Comment;

import androidx.recyclerview.widget.RecyclerView;

public class UserCommentViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;

    public UserCommentViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item);
    }

    public void bind(Comment place) {
        mTitle.setText(place.getText());
    }
}
