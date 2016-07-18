package com.apkplug.www.dispatchplug;

import android.content.Intent;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

public class LoginProcessor extends Processor {
    private BundleContext context=null;
    public LoginProcessor(BundleContext context){
        super(context);
        this.context=context;
    }
    @Override
    public void Receive(URI uri, HashMap<String, Object> parameter){
        Intent intent=new Intent();
        intent.setClass(context.getAndroidContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("msg_id",getMsgId());
        context.getAndroidContext().startActivity(intent);
    }
}
