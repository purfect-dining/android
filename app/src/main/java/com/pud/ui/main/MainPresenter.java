package com.pud.ui.main;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainModel mModel;
    private CompositeDisposable mCompositeDisposable;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        mModel = new MainModel();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onCreate() {
        mModel.onCreate();
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
        mModel.onDestroy();
    }

    @Override
    public void getPlaces() {
        Disposable disposable = mModel.placesBackendless().subscribe(places -> mView.onPlacesReceived(places),
                throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getOpenDiningTimings() {
        Disposable disposable = mModel.getOpenDiningTimingsBackendless("02/22/2019", "11:36").subscribe(diningTimingList -> mView.onOpenDiningTimingsReceived(diningTimingList),
                throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }
}
