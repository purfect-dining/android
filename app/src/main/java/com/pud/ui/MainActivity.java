package com.pud.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.pud.R;
import com.pud.presenter.MainPresenter;
import com.pud.presenter.view.MainView;
import com.pud.ui.adapter.ViewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements MainView {

    private ViewPager mPager;

    private ViewPagerAdapter mPagerAdapter;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = findViewById(R.id.viewPager);

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void setPresenter() {
        mPresenter = new MainPresenter();
        mPresenter.onCreate();
        mPresenter.attachView(this);
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
