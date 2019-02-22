package com.pud.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pud.R;
import com.pud.listener.RecyclerItemClickListener;
import com.pud.model.Place;
import com.pud.ui.auth.AuthActivity;
import com.pud.ui.place.PlaceActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View, RecyclerItemClickListener.OnItemClickListener {

    @BindView(R.id.main_place_list)
    RecyclerView mList;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    private MainPresenter mPresenter;
    private PlaceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        mPresenter = new MainPresenter(this);
        mPresenter.onCreate();
        mPresenter.getPlaces();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onPlacesReceived(List<Place> placeList) {
        mAdapter = new PlaceAdapter(this, placeList);
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
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.main_menu_login:
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("objectId", mAdapter.getList().get(position).getObjectId());
        intent.putExtra("name", mAdapter.getList().get(position).getName());
        startActivity(intent);
    }

}
