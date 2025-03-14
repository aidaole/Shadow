package com.aidaole.demo.host;

import android.app.Application;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        InitApplication.onApplicationCreate(this);
    }
}
