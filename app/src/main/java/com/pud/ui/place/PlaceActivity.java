package com.pud.ui.place;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pud.R;
import com.pud.model.Place;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceActivity extends AppCompatActivity implements PlaceContract.View {

    @BindView(R.id.place_toolbar)
    Toolbar mToolbar;

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

        String objectId = getIntent().getStringExtra("objectId");
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
        }

        return true;
    }

    @Override
    public void onPlaceLoaded(Place place) {
        this.mPlace = place;
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
