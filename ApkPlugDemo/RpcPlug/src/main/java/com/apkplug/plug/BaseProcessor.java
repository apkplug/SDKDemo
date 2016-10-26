package com.apkplug.plug;


import android.util.Log;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

/**
 * Created by qinfeng on 2016/7/20.
 */
public abstract class BaseProcessor extends Processor {

    public static BundleContext context;
    DispatchAgent dispatchAgent;

    public BaseProcessor(BundleContext contextin) {
        super(context);
        Log.e("init ","init thread "+ Thread.currentThread().getName());
        context = contextin;
        dispatchAgent = new DispatchAgent(context);
    }
}
