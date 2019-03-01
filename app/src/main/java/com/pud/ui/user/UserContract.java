package com.pud.ui.user;

import com.pud.base.BaseContract;

import io.reactivex.Observable;

public interface UserContract {

    interface View extends BaseContract.View {
        void onUserUpdated();

        void onUserLoggedOut();

        void onError(String message);
    }

    interface Model extends BaseContract.Model {
        Observable<Boolean> updateUserBackendless(String name, String email, String password);
    }

    interface Presenter extends BaseContract.Presenter {
        void updateUser(String name, String email, String password);

        void logoutUser();
    }

}
