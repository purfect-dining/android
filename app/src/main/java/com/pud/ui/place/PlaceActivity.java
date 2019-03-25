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
import com.pud.ui.comment.CommentAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceActivity extends AppCompatActivity implements PlaceContract.View, RecyclerItemClickListener.OnItemClickListener {

    @BindView(R.id.place_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.place_diningtiming_pager)
    ViewPager mPager;

    @BindView(R.id.place_comment_list)
    RecyclerView mCommentList;

    String objectId;
    CommentAdapter mAdapter;

    private PlacePresenter mPresenter;
    private DiningTimingPagerAdapter mPagerAdapter;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);

        objectId = getIntent().getStringExtra("objectId");
        String name = getIntent().getStringExtra("name");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        mPresenter = new PlacePresenter(this);
        mPresenter.onCreate();
        mPresenter.getPlace(objectId);

        mCommentList.setLayoutManager(new LinearLayoutManager(this));
        mCommentList.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
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
                intent.putExtra("comment_dining_id", mPlace.getDiningTimings().get(0).getObjectId());
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public void onPlaceLoaded(Place place) {
        this.mPlace = place;
        getSupportActionBar().setTitle(mPlace.getName() + " " + mPlace.getDiningTimings().get(0).getComments().size());

        mAdapter = new CommentAdapter(this, mPlace.getDiningTimings().get(0).getComments());
        mCommentList.setAdapter(mAdapter);

        mPagerAdapter = new DiningTimingPagerAdapter(getSupportFragmentManager(), mPlace.getDiningTimings());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Backendless.UserService.CurrentUser() != null)
            getMenuInflater().inflate(R.menu.place_toolbar, menu);
        return true;
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
