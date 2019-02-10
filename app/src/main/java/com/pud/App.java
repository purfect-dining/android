package com.pud;

import android.app.Application;

import com.backendless.Backendless;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.initApp(this, BuildConfig.BACKENDLESS_APP_ID, BuildConfig.BACKENDLESS_API_KEY);
    }

}
