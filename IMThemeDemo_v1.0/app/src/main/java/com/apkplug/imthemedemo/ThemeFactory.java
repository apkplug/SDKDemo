package com.apkplug.imthemedemo;
import org.apkplug.Bundle.Theme.ApkplugTheme;
import org.apkplug.Bundle.Theme.RegThemeChengeListener;
import org.apkplug.Bundle.Theme.ThemeAgent;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import android.content.Context;

public class ThemeFactory {
	private static ThemeAgent ta=null;
	private static ThemeFactory _instance=null;
	public ThemeFactory(final BundleContext mcontext){
		ta=new ThemeAgent(mcontext);
		
	}
	synchronized public static ThemeFactory getInstance(BundleContext mcontext){
    if(_instance==null){
    _instance=new ThemeFactory(mcontext);
    }
    return _instance;
    } 
	public ApkplugTheme getService() {
		return ta.getService();
	}
	public RegThemeChengeListener getRegThemeChengeListener() throws Exception {
		return ta.getRegThemeChengeListener();
	}
}
