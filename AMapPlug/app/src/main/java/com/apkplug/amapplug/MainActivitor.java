package com.apkplug.amapplug;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by qinfeng on 2016/7/21.
 */
public class MainActivitor implements BundleActivator {

    public static BundleContext bundleContext;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        //bundleContext.getAndroidContext().getApplicationContext();
        this.bundleContext = bundleContext;
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    }
}
