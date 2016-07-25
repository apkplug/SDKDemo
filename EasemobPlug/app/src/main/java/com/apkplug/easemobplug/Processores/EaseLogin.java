package com.apkplug.easemobplug.Processores;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.apkplug.easemobplug.R;
import com.apkplug.easemobplug.ui.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/7/20.
 */
public class EaseLogin extends BaseProcessor {
    public EaseLogin(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {

        String userName = (String) hashMap.get("UserName");
        String passWord = (String) hashMap.get("Password");

        if(userName == null || passWord == null){
            dispatchAgent.reply(getMsgId(),false,new Exception("your username or password is null"));
            return;
        }
//        EMCallBack callBack = (EMCallBack) hashMap.get("callback");
//        if(callBack == null){
//            dispatchAgent.reply(getMsgId(),false,new Exception("your callback is null"));
//            return;
//        }
        EMClient.getInstance().login(userName, passWord, new EMCallBack() {
            @Override
            public void onSuccess() {
                dispatchAgent.reply(getMsgId(),true,"success");
            }

            @Override
            public void onError(int i, String s) {
                dispatchAgent.reply(getMsgId(),false,s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
