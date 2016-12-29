package com.apkplug.www.rpc_host;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apkplug.www.rpc_host.rpc.Login;
import com.apkplug.www.rpc_host.rpc.LoginCallback;

import org.apkplug.Bundle.InstallBundler;
import org.apkplug.Bundle.InstallInfo;
import org.apkplug.Bundle.bundlerpc.BundleRPCAgent;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameworkInstance frame= FrameworkFactory.getInstance().getFrame();
        final BundleContext context =frame.getSystemBundleContext();

        textView = (TextView) findViewById(R.id.text);

        InstallBundler ib=new InstallBundler(context);
        ib.Debug(true); //设置为调试模式, 在调试模式下才会重复安装assets目录下的apk文件, 而非调试模式下只会安装一次
        InstallInfo installinfo =new InstallInfo();
        installinfo.isInstallAPK=true;  //设置可以安装普通的apk文件 , apkplug默认情况只能安装含有assets/plugin.xml的apk文件,因此对于普通的apk文件需要配置这个参数为true
        ib.installForAssets("rpc_plug-debug.apk", "100", installinfo,new installCallback(){
            //在非调试模式下 只有String assetsName, String version 这两个变量改变才会重新执行安装流程
            @Override
            public void callback(int arg0, org.osgi.framework.Bundle arg1) {
                //Log.e("status改动了一点点",""+arg0);
                if(arg0==installCallback.success_install){
                    textView.setText("hello_plug安装成功");
                }else if(arg0==installCallback.success_update){
                    textView.setText("hello_plug更新成功");
                }else if(arg0==installCallback.error_cache_not_updated){
                    textView.setText("hello_plug已安装过,且assetsName和version都未改变,不执行安装流程。" +
                            "\n测试环境可以将InstallBundler.Debug(true)让框架每次都重新安装这个apk");
                }
            }
        });


        Button login = (Button) findViewById(R.id.login);
        assert login != null;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //查询服务
                    BundleRPCAgent rpcAgent=new BundleRPCAgent(context);
                    final Login login_rpc=rpcAgent.syncCall("apkplug://rpc/login",Login.class);
                    //找到插件中的服务,调用该服务的登陆函数
                    login_rpc.login("123", "123", new LoginCallback() {
                        @Override
                        public void onSuccess(String platform, String uid, final String uname) {
                            //插件端返回结果
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"[宿主中打印]登陆成功:"+uname,Toast.LENGTH_SHORT).show();
                                    textView.setText("登陆成功:"+uname);
                                }
                            });

                        }

                        @Override
                        public void onFail(String status, final String errorMsg) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"[宿主中打印]登陆失败:"+errorMsg,Toast.LENGTH_SHORT).show();
                                    textView.setText("登陆失败:"+errorMsg);
                                }
                            });

                        }
                    });

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

    }
}
