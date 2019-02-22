package com.pud.ui.user;

import io.reactivex.disposables.CompositeDisposable;

public class UserPresenter implements UserContract.Presenter {

    private UserContract.View mView;
    private UserModel mModel;
    private CompositeDisposable mCompositeDisposable;

    public UserPresenter(UserContract.View view) {
        this.mView = view;
        mModel = new UserModel();
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

//    @Override
//    public void getPlace(String objectId) {
//        Disposable disposable = mModel.getPlaceBackendless(objectId).subscribe(place -> mView.onPlaceLoaded(place),
//                throwable -> mView.onError(throwable.getMessage()));
//
//        mCompositeDisposable.add(disposable);
//    }

}
