package com.pud.model;

import io.realm.Realm;

public abstract class Model<T> {

    private Realm mRealm;

    public void onCreate() {
        mRealm = Realm.getDefaultInstance();
    }

    public void onDestroy() {
        mRealm.close();
    }

    protected Realm getRealm() {
        return mRealm;
    }

}
