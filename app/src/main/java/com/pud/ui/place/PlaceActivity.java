package com.pud.ui.place;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.R;
import com.pud.listener.RecyclerItemClickListener;
import com.pud.model.Comment;
import com.pud.model.DiningTiming;
import com.pud.model.Place;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceActivity extends AppCompatActivity implements PlaceContract.View, RecyclerItemClickListener.OnItemClickListener {

    @BindView(R.id.place_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.place_comment_list)
    RecyclerView mList;

    String objectId;
    CommentAdapter mAdapter;
    private PlacePresenter mPresenter;
    private Place mPlace;
    private List<Comment> ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new PlacePresenter(this);
        mPresenter.onCreate();

        objectId = getIntent().getStringExtra("objectId");
        String name = getIntent().getStringExtra("name");

        getSupportActionBar().setTitle(name);
        mPresenter.getPlace(objectId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);

        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.add_comment:
                showAddItemDialog(this);
                break;
        }

        return true;
    }

    @Override
    public void onPlaceLoaded(Place place) {
        this.mPlace = place;
        ll = place.getDiningTimings().get(0).getComments();
        getSupportActionBar().setTitle(place.getName() + " " + place.getDiningTimings().get(0).getComments().size());
        mAdapter = new CommentAdapter(this, place.getDiningTimings().get(0).getComments());
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Backendless.UserService.CurrentUser() != null) {
            getMenuInflater().inflate(R.menu.place_toolbar, menu);

            return true;
        }
        return false;
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Ford Dining Court")
                .setMessage("Enter comment")
                .setView(taskEditText)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Backendless.Data.of(DiningTiming.class).findById("47373B01-FF70-D06F-FF3A-4724C499FF00", new AsyncCallback<DiningTiming>() {
                            @Override
                            public void handleResponse(DiningTiming diningTiming) {
                                Comment comment = new Comment();

                                String comText = taskEditText.getText().toString();
                                comment.setText(comText);
                                comment.setRating("4");

                                Backendless.Data.of(Comment.class).save(comment, new AsyncCallback<Comment>() {
                                    @Override
                                    public void handleResponse(Comment response) {
                                        List<BackendlessUser> ut = new ArrayList<>();
                                        ut.add(Backendless.UserService.CurrentUser());
                                        Backendless.Data.of(Comment.class).setRelation(comment, "byUser", ut, new AsyncCallback<Integer>() {
                                            @Override
                                            public void handleResponse(Integer response) {
                                                Toast.makeText(PlaceActivity.this, "Comment Posted", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void handleFault(BackendlessFault fault) {

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
                                                Toast.makeText(PlaceActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        List<DiningTiming> dt = new ArrayList<>();
                                        dt.add(diningTiming);
                                        Backendless.Data.of(Comment.class).setRelation(comment, "ofDiningTiming", dt, new AsyncCallback<Integer>() {
                                            @Override
                                            public void handleResponse(Integer response) {
                                                mPresenter.getPlace(objectId);
                                            }

                                            @Override
                                            public void handleFault(BackendlessFault fault) {
                                                Toast.makeText(PlaceActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(PlaceActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(PlaceActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Ford Dining Court")
                .setMessage("Edit comment")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dialog2 = new AlertDialog.Builder(PlaceActivity.this)
                                .setTitle("Ford Dining Court")
                                .setMessage("Are you sure you want to delete?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Comment comment = ll.get(position);
                                        ll.remove(position);

                                        Backendless.Data.of(Comment.class).remove(comment, new AsyncCallback<Long>() {
                                            @Override
                                            public void handleResponse(Long response) {
                                                mAdapter.notifyDataSetChanged();

                                                Toast.makeText(PlaceActivity.this, "Comment Deleted", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void handleFault(BackendlessFault fault) {

                                            }
                                        });
                                    }

                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog2.show();
                    }

                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText taskEditText = new EditText(PlaceActivity.this);
                        taskEditText.setText(ll.get(position).getText());
                        AlertDialog dialog1 = new AlertDialog.Builder(PlaceActivity.this)
                                .setTitle("Ford Dining Court")
                                .setMessage("Edit comment")
                                .setView(taskEditText)
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ll.get(position).setText(taskEditText.getText().toString());
                                        Comment comment = ll.get(position);

                                        Backendless.Data.save(comment, new AsyncCallback<Comment>() {
                                            @Override
                                            public void handleResponse(Comment response) {
                                                mAdapter.notifyDataSetChanged();
                                                Toast.makeText(PlaceActivity.this, "Comment Updated", Toast.LENGTH_SHORT).show();


                                            }

                                            @Override
                                            public void handleFault(BackendlessFault fault) {
                                                Toast.makeText(PlaceActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog1.show();
                    }
                })
                .create();
        dialog.show();
    }
}
