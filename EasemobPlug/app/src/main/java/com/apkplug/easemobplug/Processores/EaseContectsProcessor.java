package com.apkplug.easemobplug.Processores;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qinfeng on 2016/7/20.
 */
public class EaseContectsProcessor extends BaseProcessor {
    public EaseContectsProcessor(BundleContext context) {
        super(context);
    }

    @Override
    public void Receive(URI uri, HashMap<String, Object> hashMap) {

        new Thread(){
            @Override
            public void run() {
                List<String> allFirends = null;
                try {
                    allFirends = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    dispatchAgent.reply(getMsgId(),true,allFirends);
                } catch (HyphenateException e) {
                    dispatchAgent.reply(getMsgId(),false,e);
                }
            }
        }.start();


    }
}
