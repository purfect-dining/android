package com.pud;

import android.os.Bundle;
import android.widget.Toast;

import com.pud.presenter.MainPresenter;
import com.pud.presenter.view.MainView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
