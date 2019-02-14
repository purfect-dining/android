package com.pud.base;

import io.realm.Realm;

public interface BaseContract {

    interface View {
        void onError(String error);
    }

    interface Model {
        void onCreate();

        void onDestroy();

        Realm getRealm();
    }

    interface Presenter {
        void onCreate();

        void onDestroy();
    }

}
