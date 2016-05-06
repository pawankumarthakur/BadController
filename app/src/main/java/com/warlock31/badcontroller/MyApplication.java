package com.warlock31.badcontroller;

import android.app.Application;
import android.content.Context;

/**
 * Created by Warlock on 5/2/2016.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
}
