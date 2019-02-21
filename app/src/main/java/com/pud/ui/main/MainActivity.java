package com.pud.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pud.R;
import com.pud.listener.ViewPagerListener;
import com.pud.model.Place;
import com.pud.ui.adapter.ViewPagerAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements MainContract.View, ViewPagerListener {

    private BottomNavigationView mNav;
    private ViewPager mPager;

    private ViewPagerAdapter mPagerAdapter;
    private DataListener<List<Place>> mHomeDataListener;

    private MainPresenter mPresenter;

    private int lastNavPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNav = findViewById(R.id.bottomNav);
        mPager = findViewById(R.id.viewPager);

        mPresenter = new MainPresenter(this);
        mPresenter.onCreate();

        lastNavPosition = 0;
        mNav.getMenu().getItem(0).setChecked(true);
        mNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    mPager.setCurrentItem(0);
                    break;
                case R.id.nav_second:
                    mPager.setCurrentItem(1);
                    break;
                case R.id.nav_third:
                    mPager.setCurrentItem(2);
                    break;
            }
            return false;
        });

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPager.removeOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {
        mNav.getMenu().getItem(lastNavPosition).setChecked(false);
        mNav.getMenu().getItem(position).setChecked(true);
        lastNavPosition = position;
    }

    @Override
    public void onPlacesReceived(List<Place> placeList) {
        mHomeDataListener.onDataReceived(placeList);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setHomeDataListener(DataListener<List<Place>> dataListener) {

    }
    public interface DataListener<T> {
        void onDataReceived(T data);
    }

}
