package com.apkplug.amappluguserxxxxxxxxxxxxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugInfo;

import org.apkplug.app.FrameworkFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";


        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab == null){
            return;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
new Thread(){

    @Override
    public void run() {

        PlugManager.getInstance().installAssets(MainActivity.this, "app-debug.apk", "1.0.0", new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.apkplug.amapplug.MenuActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onInstallFailuer(int i, PlugInfo plugInfo, String s) {
                Log.e("installf","i="+i+"ss:"+s);
            }

            @Override
            public void onDownloadFailure(String s) {

            }
        });

    }
}.start();


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.apkplug.amapplug.MenuActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
