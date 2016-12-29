package com.apkplug.www.apkplug_examples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apkplug.Bundle.InstallBundler;
import org.apkplug.Bundle.InstallInfo;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView= (TextView) this.findViewById(R.id.textView);
        InstallBundler ib=new InstallBundler(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
        ib.Debug(true); //设置为调试模式, 在调试模式下才会重复安装assets目录下的apk文件, 而非调试模式下只会安装一次
        InstallInfo installinfo =new InstallInfo();
        installinfo.isInstallAPK=true;  //设置可以安装普通的apk文件 , apkplug默认情况只能安装含有assets/plugin.xml的apk文件,因此对于普通的apk文件需要配置这个参数为true
        ib.installForAssets("hello_plug-debug.apk", "100", installinfo,new installCallback(){
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
        Button start = (Button) this.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得所有apkplug中的插件对象
                org.osgi.framework.Bundle[] all_bundles=FrameworkFactory.getInstance().getFrame().getSystemBundleContext().getBundles();
                org.osgi.framework.Bundle plug=all_bundles[1]; //取出第二个插件, 因为第一个插件永远是由apkplug自动生成的SystemBundle
                String plugMianActivity=plug.getBundleActivity().split(",")[0]; //获取插件的启动Activity
                Intent intent=new Intent();
                intent.setClassName(MainActivity.this,plugMianActivity);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
