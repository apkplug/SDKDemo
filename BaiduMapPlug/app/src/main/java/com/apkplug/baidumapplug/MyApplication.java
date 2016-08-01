package com.apkplug.baidumapplug;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by qinfeng on 2016/7/29.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
