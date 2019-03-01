package com.pud.ui.user;

import com.backendless.Backendless;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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

    @Override
    public void updateUser(String name, String email, String password) {
        Disposable disposable = mModel.updateUserBackendless(name, email, password).subscribe(
                logout -> {
                    if (logout) mView.onUserUpdated();
                    else logoutUser();
                },
                throwable -> mView.onError(throwable.getMessage()));

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void logoutUser() {
        Backendless.UserService.logout();
        mView.onUserLoggedOut();
    }

}
