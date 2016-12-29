package com.apkplug.www.apkplug_examples;

import android.app.Application;

import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;

/**
 * Created by love on 16/12/2.
 */
public class MyAppliction extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化 apkplug sdk 本项目使用的是 apkplug v3.9.3版本,正式环境中请尽量使用最新版本的SDK ,apkplug通常保持一个月一个版本的进度
        try {
            FrameworkInstance frame= FrameworkFactory.getInstance().start(null,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
