package com.apkplug.easemobplug.Processores;

import android.content.Intent;

import com.apkplug.easemobplug.ui.LoginActivity;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/8/25.
 */
public class LoginProcessor extends BaseProcessor{
    public LoginProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        try {
            Intent intent = new Intent(context.getBundleContext(), LoginActivity.class);
            context.getBundleContext().startActivity(intent);
            dispatchAgent.reply(getMsgId(),true);
        } catch (Exception e) {
            e.printStackTrace();
            dispatchAgent.reply(getMsgId(),false,e);
        }
    }
}
