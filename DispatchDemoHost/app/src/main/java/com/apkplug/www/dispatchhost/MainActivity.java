package com.apkplug.www.dispatchhost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.NotFoundException;
import org.apkplug.Bundle.dispatch.RunException;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        Button login = (Button) findViewById(R.id.login);
        FrameworkInstance frame= FrameworkFactory.getInstance().getFrame();
        BundleContext context =frame.getSystemBundleContext();

        final DispatchAgent dispatchAgent=new DispatchAgent(context);
        dispatchAgent.register(context,"http://yyfr.net/host/accesstoken",TokenProcessor.class.getName());//注册一个dispatch服务,等插件来调用

        //调用插件的dispatch服务
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchAgent.call("http://yyfr.net/plug01/login",null,new WorkerCallback(){

                    @Override
                    public void reply(URI uri,Object ...replyObj) throws Exception {
                        String result_code=(String)replyObj[0];
                        if(result_code.equals("success")) {
                            textView.setText("登陆成功了 插件服务返回的值为 :"+(String) replyObj[1]);
                        }
                    }

                    @Override
                    public void timeout(URI uri) throws Exception {
                        textView.setText("调用登陆接口超时");
                    }

                    @Override
                    public void Exception(URI uri,Throwable throwable) {
                        textView.setText("调用登陆接口失败:"+throwable.getMessage());
                    }
                });
            }
        });
    }
}
