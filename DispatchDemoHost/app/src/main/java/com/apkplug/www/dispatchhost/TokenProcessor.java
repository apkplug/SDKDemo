package com.apkplug.www.dispatchhost;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

public class TokenProcessor extends Processor {
    public TokenProcessor(BundleContext context){
        super(context);
    }
    @Override
    public void Receive(URI uri, HashMap<String, Object> parameter){
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reply("success","access token");
            }
        }.start();
    }
}
