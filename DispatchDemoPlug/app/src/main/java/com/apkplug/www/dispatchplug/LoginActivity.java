package com.apkplug.www.dispatchplug;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        final int msg_id=this.getIntent().getIntExtra("msg_id",-1);
        final TextView textView = (TextView) findViewById(R.id.text);
        Button gettoken = (Button) findViewById(R.id.gettoken);
        gettoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginActivity.this, "登陆中...", "正在获取宿主中的token... 请稍后！");
                final BundleContext context =SimpleBundle.mcontext;
                final DispatchAgent dispatchAgent=new DispatchAgent(context);
                dispatchAgent.call("http://yyfr.net/host/accesstoken",null,new WorkerCallback(){

                    @Override
                    public void reply(URI uri, Object ...replyObj) throws Exception {
                        String result_code=(String)replyObj[0];
                        if(result_code.equals("success")) {
                            textView.setText("token :"+(String) replyObj[1]);
                        }
                        dialog.dismiss();
                        //回复登陆dispatch服务
                        dispatchAgent.reply(msg_id,"success","login success user_id = sss");
                        finish();
                    }

                    @Override
                    public void timeout(URI uri) throws Exception {
                        dialog.dismiss();
                        dispatchAgent.reply(msg_id,"fail","获取 token 超时");
                        finish();
                    }

                    @Override
                    public void Exception(URI uri,Throwable throwable) {
                        dialog.dismiss();
                        dispatchAgent.reply(msg_id,"fail","获取 token 失败 :"+throwable.getMessage());
                        finish();
                    }
                });
            }
        });
    }
}
