package com.apkplug.baseplugmodle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugInfo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.apkplug.app.FrameworkFactory;
import org.osgi.framework.Bundle;

/**
 * Created by qinfeng on 2016/9/2.
 */
public class MainApplication extends Application {

    //change
    public static String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";

    public static RefWatcher getRefWatcher(Context context) {
        MainApplication application = (MainApplication) context.getApplicationContext();
        return application.refWatcher;
    }
    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);

            PlugManager.getInstance().installAssets(this, "RpcPlug-debug.apk", "1.0.0", new OnInstallListener() {
                @Override
                public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

                }

                @Override
                public void onInstallSuccess(Bundle bundle, PlugInfo plugInfo) {
                    Log.e("install s",bundle.getName());
                }

                @Override
                public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
                    Log.e("install f",s);
                }

                @Override
                public void onDownloadFailure(String s) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
