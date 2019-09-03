package com.android.seetaoism.app;

import android.app.Application;

public class BaseApp extends Application {
    private static BaseApp baseApp;

    public static BaseApp getBaseApp() {
        return baseApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;

    }
}
