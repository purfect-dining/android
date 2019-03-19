package com.pud.ui.place;

import android.view.View;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.Comment;

import androidx.recyclerview.widget.RecyclerView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;

    public CommentViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item);
    }

    public void bind(Comment comment) {
        mTitle.setText(comment.getByUser().getProperty("name") + ": " + comment.getText());
    }
}
