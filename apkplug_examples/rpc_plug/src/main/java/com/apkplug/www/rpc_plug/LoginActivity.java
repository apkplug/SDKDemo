package com.apkplug.www.rpc_plug;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apkplug.www.rpc_plug.rpc.LoginCallback;

import org.apkplug.Bundle.bundlerpc.ObjectPool;
import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.osgi.framework.BundleContext;

import java.net.URI;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //LoginRPC 传递过来的LoginCallback
        ObjectPool<LoginCallback> pool= (ObjectPool) this.getIntent().getSerializableExtra("callback");
        //取出LoginCallback对象,并将这个引用从ObjectPool中移除
        final LoginCallback callback=pool.popObject();

        final TextView textView = (TextView) findViewById(R.id.text);
        Button gettoken = (Button) findViewById(R.id.gettoken);
        gettoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginActivity.this, "登陆中...", "正在与服务器通信... 请稍后！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        callback.onSuccess("weixin","123456","apkplug");
                        finish();
                    }
                }).start();
            }
        });
    }
}
