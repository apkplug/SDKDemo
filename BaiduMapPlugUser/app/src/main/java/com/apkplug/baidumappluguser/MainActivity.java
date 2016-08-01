package com.apkplug.baidumappluguser;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.data.PlugInfo;

import org.apkplug.app.FrameworkFactory;
import org.osgi.framework.BundleException;
import org.osgi.framework.StartCallback;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String PUBLICKEY = "MIGdMA0GCSqGSIb3DQEBAQUAA4GLADCBhwKBgQDznY/txkI/prtOi3pofTkhu6bdPKucyRzvQnkqsv/FNGhos0+QwPCy17PT8ftP60PUyLXzTiF5PW901sEJYHx8KVc/b+j41rvXdgVGJ/i8t26vxZR7FxKnuxc9TjJ3IFSvhiZY6NaOGf9l/qv6xbD+s6SEMZqBR40q2lpUe0VorwIBAw==";

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        try {
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null,this).getSystemBundleContext(),PUBLICKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                        intent.setClassName(MainActivity.this, "com.apkplug.baidumapplug.MainActivity");
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
        if(button == null){
            return;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.osgi.framework.Bundle[] bundles = FrameworkFactory.getInstance().getFrame().getSystemBundleContext().getBundles();
                for(org.osgi.framework.Bundle bundle : bundles){
                    if(bundle.getName().equals("BaiduMapPlug")){

                        bundle.start(new StartCallback() {
                            @Override
                            public void onSuccess(org.osgi.framework.Bundle bundle) {
                                Intent intent = new Intent();
                                intent.setClassName(MainActivity.this, "com.apkplug.baidumapplug.MainActivity");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Log.e(this.getClass().getName(),bundle.getName());
                            }

                            @Override
                            public void onFail(org.osgi.framework.Bundle bundle, Throwable throwable) {
                                StringWriter sw = new StringWriter();
                                PrintWriter pw = new PrintWriter(sw);
                                throwable.printStackTrace(pw);

                                System.out.println(sw.toString()); // stack trace as a string
                                Log.e(this.getClass().getName(),throwable.toString());
                            }
                        });
                    }
                }


            }
        });
    }
}
