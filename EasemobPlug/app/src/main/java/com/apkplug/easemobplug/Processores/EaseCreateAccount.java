package com.apkplug.easemobplug.Processores;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/7/20.
 */
public class EaseCreateAccount extends BaseProcessor {
    public EaseCreateAccount(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        String userName = (String) hashMap.get("UserName");
        String password = (String) hashMap.get("Password");
        if(userName == null || password == null){
            dispatchAgent.reply(getMsgId(),false,new Exception("username or password is null"));
            return;
        }
        try {
            EMClient.getInstance().createAccount(userName,password);
            dispatchAgent.reply(getMsgId(),true,"success");
        } catch (HyphenateException e) {
            dispatchAgent.reply(getMsgId(),false,e);
        }
    }
}
