package com.apkplug.imthemedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class ProxyApplication extends Application {
	private FrameworkInstance frame=null;

	public FrameworkInstance getFrame() {
		return frame;
	}

	public void onCreate() {   
		 super.onCreate(); 
		 try
	        {
			 frame=FrameworkFactory.getInstance().start(null,this);
			 BundleContext context =frame.getSystemBundleContext();
			 InstallBundle ib=new InstallBundle(context);
				ib.installBundle(getApplicationContext(), context, 
						new installCallback(){
							@Override
							public void callback(int arg0, Bundle arg1) {
								if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
									Log.d("",String.format("插件安装 %s ： %d",arg1.getName(),arg0));
									return;
								}
								else{
									Log.d("","插件安装失败 ：%s"+arg1.getName());
								}
							}
				});
			 ThemeFactory.getInstance(frame.getSystemBundleContext());
	        }
	        catch (Exception ex)
	        {
	            System.err.println("Could not create : " + ex);
	            ex.printStackTrace();
	            int nPid = android.os.Process.myPid();
				android.os.Process.killProcess(nPid);
	        }
	}
}
