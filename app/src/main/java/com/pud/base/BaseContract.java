package com.pud.base;

import io.realm.Realm;

public interface BaseContract {

    interface View {

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
