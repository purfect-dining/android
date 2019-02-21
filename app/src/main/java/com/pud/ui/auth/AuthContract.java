package com.pud.ui.auth;

import com.backendless.BackendlessUser;
import com.pud.base.BaseContract;

import io.reactivex.Observable;

public interface AuthContract {

    interface View extends BaseContract.View {
        void onSuccess(AuthActivity.AuthType type, String message);

        void onError(String message);
    }

    interface Model extends BaseContract.Model {
        Observable<BackendlessUser> loginBackendless(String email, String password);

        Observable<BackendlessUser> signupBackendless(String email, String password, String name);

        Observable<Boolean> recoverBackendless(String email);

        void loginRealm(BackendlessUser user);
    }

    interface Presenter extends BaseContract.Presenter {
        void login(String email, String password);

        void signup(String email, String password, String name);

        void recover(String email);
    }

}
