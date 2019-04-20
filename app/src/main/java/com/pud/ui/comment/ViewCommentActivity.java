package com.pud.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewCommentActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

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
        commentList.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

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
            case R.id.comments:
                Intent intent = new Intent(this, ModCommentActivity.class);
                intent.putExtra("comment_dining_id", getIntent().getStringExtra("put_comment_id"));
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        likeComment(position);
        // Delete Comment
//        AlertDialog dialog = new AlertDialog.Builder(PlaceActivity.this)
//                .setTitle("Ford Dining Court")
//                .setMessage("Are you sure you want to delete?")
//                .setPositiveButton("Yes", (dialog2, which) -> {
//                    Comment comment = mAdapter.getList().get(position);
//                    mAdapter.getList().remove(position);
//
//                    Backendless.Data.of(Comment.class).remove(comment, new AsyncCallback<Long>() {
//                        @Override
//                        public void handleResponse(Long response) {
//                            mAdapter.notifyDataSetChanged();
//
//                            Toast.makeText(PlaceActivity.this, "Comment Deleted", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void handleFault(BackendlessFault fault) {
//
//                        }
//                    });
//                })
//                .setNegativeButton("Cancel", null)
//                .create();
//        dialog.show();
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
}
