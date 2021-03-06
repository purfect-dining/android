package com.pud.base;

import io.realm.Realm;

public abstract class BaseModel implements BaseContract.Model {

    private Realm mRealm;

    @Override
    public void onCreate() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        mRealm.close();
    }

    @Override
    public Realm getRealm() {
        return mRealm;
    }

}
