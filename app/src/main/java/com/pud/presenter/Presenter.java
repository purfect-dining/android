package com.pud.presenter;

import com.pud.presenter.view.View;

import androidx.annotation.CallSuper;

abstract class Presenter<T extends View> {

    private T mView;

    @CallSuper
    void onCreate() {

    }

    @CallSuper
    void onDestroy() {

    }

    public void attachView(T view) {
        mView = view;
    }

    void onSuccess(String message) {
        if (mView != null) mView.onSuccess(message);
    }

    void onError(String message) {
        if (mView != null) mView.onError(message);
    }

}