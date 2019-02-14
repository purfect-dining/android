package com.pud.ui.login;

import com.pud.base.BaseContract;

public interface LoginContract {

    interface View extends BaseContract.View {
        void onLoginSuccess();

        void onLoginFailed(String error);

        void loginCheck();
    }

    interface Model extends BaseContract.Model {

    }

    interface Presenter extends BaseContract.Presenter {
        void login(String email, String password);
    }

}
