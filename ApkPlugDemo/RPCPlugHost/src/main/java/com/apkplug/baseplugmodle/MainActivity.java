package com.apkplug.baseplugmodle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.Bundle.bundlerpc.functions.Action2;
import org.apkplug.app.FrameworkFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BundleRPCAgent agent = new BundleRPCAgent(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());

        try {
            RpcTest rpcTest = agent.syncCall("apkplug://plug/rpc",RpcTest.class);

            rpcTest.test("host", new IHostBean() {
                @Override
                public String getHostName() {
                    return "host";
                }

                @Override
                public int getId() {
                    return 2;
                }
            }, new Action2<String, IPlugBean>() {
                @Override
                public void call(String s, IPlugBean iPlugBean) {
                    Log.e("action2",s+" "+iPlugBean.getName());
                }
            });

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }


    private void startActivity(String activity) {
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this,activity);
        startActivity(intent);
    }

    private void callDispatch(){

    }
}
