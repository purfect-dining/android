package com.pud.ui.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pud.R;
import com.pud.model.Place;
import com.pud.ui.comment.CommentAdapter;
import com.pud.ui.comment.ViewCommentActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceActivity extends AppCompatActivity implements PlaceContract.View {

    @BindView(R.id.place_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.place_diningtiming_pager)
    ViewPager mPager;

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);

        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.comments:
                Intent intent = new Intent(this, ViewCommentActivity.class);
                intent.putExtra("place_id", mPlace.getObjectId());
                intent.putExtra("put_comment_id", mPlace.getDiningTimings().get(0).getObjectId());
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public void onPlaceLoaded(Place place) {
        this.mPlace = place;
        getSupportActionBar().setTitle(mPlace.getName() + " " + mPlace.getDiningTimings().get(0).getComments().size());
        mPagerAdapter = new DiningTimingPagerAdapter(getSupportFragmentManager(), mPlace);
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_toolbar, menu);
        return true;
    }

}
