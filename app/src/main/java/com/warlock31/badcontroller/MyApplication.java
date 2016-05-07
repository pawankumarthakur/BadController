package com.warlock31.badcontroller;

import android.app.Application;
import android.content.Context;

import com.warlock31.badcontroller.database.WordPressPostDatabase;

/**
 * Created by Warlock on 5/2/2016.
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    private static WordPressPostDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = new WordPressPostDatabase(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }


    public synchronized static WordPressPostDatabase getWritableDatabase() {

        if (database == null){
            database = new WordPressPostDatabase(getAppContext());
        }

        return database;
    }
}
