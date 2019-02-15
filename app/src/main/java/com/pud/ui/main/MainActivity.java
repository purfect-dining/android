package com.pud.ui.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pud.R;
import com.pud.ui.adapter.ViewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private BottomNavigationView mNav;
    private ViewPager mPager;

    private ViewPagerAdapter mPagerAdapter;

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
            }
            return false;
        });

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mNav.getMenu().getItem(lastNavPosition).setChecked(false);
                mNav.getMenu().getItem(position).setChecked(true);
                lastNavPosition = position;
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

}
