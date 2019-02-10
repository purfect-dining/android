package com.pud.presenter.view;

public interface View {

    void setPresenter();

    void onSuccess(String message);

    void onError(String error);

}
