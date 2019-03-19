package com.pud.ui.user;

import com.pud.base.BaseContract;
import com.pud.model.Comment;

import java.util.List;

import io.reactivex.Observable;

public interface UserContract {

    interface View extends BaseContract.View {
        void onUserUpdated();

        void onUserLoggedOut();

        void onCommentsLoaded(List<Comment> commentList);

        void onError(String message);
    }

    interface Model extends BaseContract.Model {
        Observable<Boolean> updateUserBackendless(String name, String email, String password);

        Observable<List<Comment>> userCommentsBackendless();
    }

    interface Presenter extends BaseContract.Presenter {
        void updateUser(String name, String email, String password);

        void logoutUser();

        void getUserComments();
    }

}
