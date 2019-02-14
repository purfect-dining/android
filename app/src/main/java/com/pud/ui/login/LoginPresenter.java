package com.pud.ui.login;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private LoginModel mModel;

    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        mModel = new LoginModel();
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
