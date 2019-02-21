package com.pud.ui.auth;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.base.BaseModel;

import io.reactivex.Observable;

public class AuthModel extends BaseModel implements AuthContract.Model {

    @Override
    public Observable<BackendlessUser> loginBackendless(String email, String password) {
        return Observable.create(emitter ->
                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Backendless.UserService.setCurrentUser(response);
                        emitter.onNext(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        emitter.onError(setError(fault.getMessage()));
                    }
                }, true));
    }

    @Override
    public Observable<BackendlessUser> signupBackendless(String email, String password, String name) {
        return Observable.create(emitter -> {
            BackendlessUser user = new BackendlessUser();
            user.setEmail(email);
            user.setPassword(password);
            user.setProperty("name", name);

            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Backendless.UserService.setCurrentUser(response);
                    emitter.onNext(response);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    emitter.onError(setError(fault.getMessage()));
                }
            });
        });
    }

    @Override
    public Observable<Boolean> recoverBackendless(String email) {
        return Observable.create(emitter ->
                Backendless.UserService.restorePassword(email, new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        emitter.onNext(true);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        emitter.onError(setError(fault.getMessage()));
                    }
                }));
    }

    @Override
    public void loginRealm(BackendlessUser user) {

    }

}
