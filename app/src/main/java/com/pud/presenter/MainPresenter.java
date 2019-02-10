package com.pud.presenter;

import com.pud.model.MainModel;
import com.pud.presenter.view.MainView;

public class MainPresenter extends Presenter<MainView> {

    private MainModel mModel;

    @Override
    public void onCreate() {
        super.onCreate();
        mModel = new MainModel();
        mModel.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mModel.onDestroy();
    }

}
