package com.apkplug.easemobplug;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMOptions;
//import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by qinfeng on 2016/7/19.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();

        EaseUI.getInstance().init(this, options);

        //xugai
        EMClient.getInstance().setDebugMode(false);

    }
}
