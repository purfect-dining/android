package com.pud.ui.auth;

public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View mView;
    private AuthModel mModel;

    public AuthPresenter(AuthContract.View view) {
        this.mView = view;
        mModel = new AuthModel();
    }

    @Override
    public void onCreate() {
        mModel.onCreate();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }

    @Override
    public void login(String email, String password) {
        mView.onLoginSuccess();
    }
}
