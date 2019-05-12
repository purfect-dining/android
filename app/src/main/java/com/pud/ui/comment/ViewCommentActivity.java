package com.pud.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.pud.R;
import com.pud.listener.RecyclerItemClickListener;
import com.pud.model.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewCommentActivity extends AppCompatActivity implements RecyclerItemClickListener.ClickListener {

    @BindView(R.id.comment_toolbar)
    Toolbar mToolbar;
    CommentAdapter adapter;
    private String mPlaceID;
    private List<Comment> mCommentList;
    private RecyclerView commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mPlaceID = getIntent().getStringExtra("place_id");
        commentList = findViewById(R.id.comment_list);
        commentList.setLayoutManager(new LinearLayoutManager(this));
        commentList.addOnItemTouchListener(new RecyclerItemClickListener(this, commentList, this));

        loadComments();
    }

    private void loadComments() {
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause("ofDiningTiming.ofPlace.objectId = '" + mPlaceID + "'");
        Backendless.Data.of(Comment.class).find(queryBuilder, new AsyncCallback<List<Comment>>() {
            @Override
            public void handleResponse(List<Comment> response) {
                System.out.println(response.size());
                for (Comment comment : response) {
                    System.out.println(comment.getText());
                }
                mCommentList = response;
                adapter = new CommentAdapter(ViewCommentActivity.this, response);
                commentList.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ViewCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadComments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Backendless.UserService.CurrentUser() != null)
            getMenuInflater().inflate(R.menu.comment_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);

        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.comment_menu_post:
                Intent intent = new Intent(this, ModCommentActivity.class);
                intent.putExtra("comment_dining_id", getIntent().getStringExtra("put_comment_id"));
                startActivity(intent);
                break;
        }

        return true;
    }

    private void likeComment(int position) {
        Log.e("KING", "likeComment");
        if (Backendless.UserService.CurrentUser() != null) {
            Log.e("KING", "not null");
            Comment comment = mCommentList.get(position);
            List<BackendlessUser> users = new ArrayList<>();
            users.add(Backendless.UserService.CurrentUser());
            Log.e("KING", "going backendless");
            Backendless.Data.of(Comment.class).addRelation(comment, "likes", users, new AsyncCallback<Integer>() {
                @Override
                public void handleResponse(Integer response) {
                    Log.e("KING", "Done");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("KING", "Error");
                    Toast.makeText(ViewCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mCommentList.get(position).getLikes().add(new BackendlessUser());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, ModCommentActivity.class);
        intent.putExtra("comment_dining_id", getIntent().getStringExtra("put_comment_id"));
        intent.putExtra("comment_edit", true);
        intent.putExtra("comment_id", mCommentList.get(position).getObjectId());
        intent.putExtra("comment_text", mCommentList.get(position).getText());
        intent.putExtra("comment_rating", mCommentList.get(position).getRating());
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {
        likeComment(position);
    }
}
