package com.aita;

import android.app.Application;
import android.content.Context;

/**
 * romanabuzyarov
 * 11.07.17
 */

public class AitaApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    public static Context getContext() {
        return mContext;
    }
}

