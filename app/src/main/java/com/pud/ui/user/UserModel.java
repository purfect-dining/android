package com.pud.ui.user;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.pud.base.BaseModel;
import com.pud.model.Comment;

import java.util.List;

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

    @Override
    public Observable<List<Comment>> userCommentsBackendless() {
        return Observable.create(emitter -> {
            BackendlessUser user = Backendless.UserService.CurrentUser();

            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause("byUser.objectId = '" + user.getObjectId() + "'");
            Backendless.Data.of(Comment.class).find(queryBuilder, new AsyncCallback<List<Comment>>() {
                @Override
                public void handleResponse(List<Comment> response) {
                    emitter.onNext(response);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    emitter.onError(setError(fault.getMessage()));
                }
            });
        });
    }

}
