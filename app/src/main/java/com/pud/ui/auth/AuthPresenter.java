package com.pud.ui.auth;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View mView;
    private AuthModel mModel;
    private CompositeDisposable mCompositeDisposable;

    public AuthPresenter(AuthContract.View view) {
        this.mView = view;
        mModel = new AuthModel();
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
    public void login(String email, String password) {
        Disposable disposable = mModel.loginBackendless(email, password)
                .subscribe(user -> mView.onSuccess(AuthActivity.AuthType.LOGIN, "Logged In"),
                        throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void signup(String email, String password, String name) {
        Disposable disposable = mModel.signupBackendless(email, password, name)
                .subscribe(user -> mView.onSuccess(AuthActivity.AuthType.LOGIN, "Signed Up"),
                        throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void recover(String email) {
        Disposable disposable = mModel.recoverBackendless(email)
                .subscribe(user -> mView.onSuccess(AuthActivity.AuthType.LOGIN, "Recovery Email Sent"),
                        throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

}
