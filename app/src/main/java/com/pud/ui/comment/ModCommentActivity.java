package com.pud.ui.comment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.R;
import com.pud.model.Comment;
import com.pud.model.DiningTiming;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ModCommentActivity extends AppCompatActivity {

    @BindView(R.id.comment_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.comment_input_text)
    EditText mCommentEditText;

    private String mDiningTimingID;
    private String mCommentID;
    private String mCommentText;
    private int mCommentRating;
    private boolean mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mDiningTimingID = getIntent().getStringExtra("comment_dining_id");
        mEdit = getIntent().getBooleanExtra("comment_edit", false);

        if (mEdit) {
            mCommentID = getIntent().getStringExtra("comment_id");
            mCommentText = getIntent().getStringExtra("comment_text");
            mCommentRating = getIntent().getIntExtra("comment_rating", 0);
            mToolbar.setTitle(getString(R.string.comment_edit_title));
            mCommentEditText.setText(mCommentText);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.comment_menu_post:
                if (mEdit) {
                    editComment();
                } else {
                    if (mCommentEditText.getText().toString().contains("ass")) {
                        Toast.makeText(this, "Comment contains obscene comment", Toast.LENGTH_SHORT).show();
                    } else {
                        addComment();
                    }
                }
                break;
        }
        return true;
    }

    public void addComment() {
        Backendless.Data.of(DiningTiming.class).findById(mDiningTimingID, new AsyncCallback<DiningTiming>() {
            @Override
            public void handleResponse(DiningTiming diningTiming) {
                Comment comment = new Comment();

                String comText = mCommentEditText.getText().toString();
                comment.setText(comText);
                comment.setRating(4);

                Backendless.Data.of(Comment.class).save(comment, new AsyncCallback<Comment>() {
                    @Override
                    public void handleResponse(Comment response) {
                        List<BackendlessUser> ut = new ArrayList<>();
                        ut.add(Backendless.UserService.CurrentUser());
                        Backendless.Data.of(Comment.class).setRelation(comment, "byUser", ut, new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(ModCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        List<Comment> ct = new ArrayList<>();
                        ct.add(comment);
                        Backendless.Data.of(DiningTiming.class).addRelation(diningTiming, "comments", ct, new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(ModCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        List<DiningTiming> dt = new ArrayList<>();
                        dt.add(diningTiming);
                        Backendless.Data.of(Comment.class).setRelation(comment, "ofDiningTiming", dt, new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {
                                Toast.makeText(ModCommentActivity.this, "Comment Posted", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(ModCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(ModCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ModCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editComment() {
        Comment comment = new Comment();
        comment.setObjectId(mCommentID);
        comment.setText(mCommentEditText.getText().toString().trim());
        comment.setRating(mCommentRating);

        Backendless.Data.save(comment, new AsyncCallback<Comment>() {
            @Override
            public void handleResponse(Comment response) {
                Toast.makeText(ModCommentActivity.this, "Comment Updated", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ModCommentActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
