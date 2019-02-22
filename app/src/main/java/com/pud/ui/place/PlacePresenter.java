package com.pud.ui.place;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PlacePresenter implements PlaceContract.Presenter {

    private PlaceContract.View mView;
    private PlaceModel mModel;
    private CompositeDisposable mCompositeDisposable;

    public PlacePresenter(PlaceContract.View view) {
        this.mView = view;
        mModel = new PlaceModel();
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
    public void getPlace(String objectId) {
        Disposable disposable = mModel.getPlaceBackendless(objectId).subscribe(place -> mView.onPlaceLoaded(place),
                throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

}
