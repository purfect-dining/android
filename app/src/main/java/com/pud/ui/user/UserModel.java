package com.pud.ui.user;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.pud.base.BaseModel;

import io.reactivex.Observable;


public class UserModel extends BaseModel implements UserContract.Model {

    @Override
    public Observable<Boolean> updateUserBackendless(String name, String email, String password) {
        return Observable.create(emitter -> {
            BackendlessUser user = Backendless.UserService.CurrentUser();
            user.setProperty("name", name);
            user.setEmail(email);
            if (password.length() > 0) user.setPassword(password);

            Backendless.Data.save(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    emitter.onNext(password.length() > 0);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    emitter.onError(setError(fault.getMessage()));
                }
            });
        });
    }

}
