package com.apkplug.easemobplug.ui;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.hyphenate.easeui.ui.EaseBaseActivity;

/**
 * Created by qinfeng on 2016/7/19.
 */
public class BaseActivity extends EaseBaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        System.err.println("base on create");
    }
}
