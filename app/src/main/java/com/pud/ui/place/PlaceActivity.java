package com.pud.ui.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.R;
import com.pud.listener.RecyclerItemClickListener;
import com.pud.model.Comment;
import com.pud.model.Place;
import com.pud.ui.comment.CommentActivity;

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
                Intent intent = new Intent(this, CommentActivity.class);
                startActivityForResult(intent, 777);
                break;
        }

        return true;
    }

    @Override
    public void onPlaceLoaded(Place place) {
        this.mPlace = place;
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

    @Override
    public void onItemClick(View view, int position) {
        AlertDialog dialog = new AlertDialog.Builder(PlaceActivity.this)
                .setTitle("Ford Dining Court")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Yes", (dialog2, which) -> {
                    Comment comment = mAdapter.getList().get(position);
                    mAdapter.getList().remove(position);

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
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

}
