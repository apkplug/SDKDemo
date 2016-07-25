package com.apkplug.easemobplug.Processores;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by qinfeng on 2016/7/20.
 */
public class EaseInit extends BaseProcessor{

    public EaseInit(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {
        try {
            EMOptions options = new EMOptions();
            System.err.println("in call");
            options.setAcceptInvitationAlways((Boolean) hashMap.get("auto_access"));
            EaseUI.getInstance().init(context.getBundleContext(), options);
            System.err.println("option:"+hashMap.get("auto_access"));
            EMClient.getInstance().setDebugMode((Boolean) hashMap.get("debug"));
            System.err.println("debug:"+hashMap.get("debug"));

            dispatchAgent.reply(getMsgId(),true,"success");
        }catch (Exception e){
            dispatchAgent.reply(getMsgId(),false,e);
        }

    }
}
