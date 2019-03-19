package com.pud.ui.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);

        Disposable disposable = mModel.getOpenDiningTimingsBackendless(dateFormat.format(date), timeFormat.format(date)).subscribe(
                diningTimingList -> mView.onOpenDiningTimingsReceived(diningTimingList),
                throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }
}
