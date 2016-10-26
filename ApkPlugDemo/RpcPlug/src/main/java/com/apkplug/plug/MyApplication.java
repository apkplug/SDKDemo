package com.apkplug.plug;

import android.app.Application;
import android.content.Context;

/**
 * Created by qinfeng on 2016/9/8.
 */
public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
