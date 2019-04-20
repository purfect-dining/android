package com.pud.ui.comment;

import android.view.View;
import android.widget.TextView;

import com.pud.R;
import com.pud.model.Comment;

import androidx.recyclerview.widget.RecyclerView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mAuthor;
    private TextView mLikes;

    public CommentViewHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.comment_text);
        mAuthor = itemView.findViewById(R.id.comment_author);
        mLikes = itemView.findViewById(R.id.comment_likes);
    }

    public void bind(Comment comment) {
        mTitle.setText(comment.getText());
        mAuthor.setText((String) comment.getByUser().getProperty("name"));
        mLikes.setText("" + comment.getLikes().size());
//        mTitle.setText("sdscsdc");
//        mAuthor.setText("sdscsdc");
//        mLikes.setText("sdscsdc");
    }
}
