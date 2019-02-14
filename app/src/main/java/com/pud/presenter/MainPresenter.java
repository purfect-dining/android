package com.pud.presenter;

import com.pud.model.MainModel;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainModel mModel;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        mModel = new MainModel();
    }

    @Override
    public void onCreate() {
        mModel.onCreate();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

}
