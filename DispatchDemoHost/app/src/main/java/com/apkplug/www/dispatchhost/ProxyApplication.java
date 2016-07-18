package com.apkplug.www.dispatchhost;

import android.app.Application;
import android.util.Log;
import org.apkplug.Bundle.InstallBundler;
import org.apkplug.Bundle.InstallInfo;
import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.Calendar;


public class ProxyApplication extends Application {

	public void onCreate() {

		Log.e("ProxyApplication", this.getApplicationContext().getPackageName());
		try {
			super.onCreate(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 try
	        {

				FrameworkInstance frame=FrameworkFactory.getInstance().start(null, this);
				final BundleContext context =frame.getSystemBundleContext();
				InstallBundler ib=new InstallBundler(context);
				ib.Debug(false);
				InstallInfo installinfo =new InstallInfo();
				installinfo.isInstallAPK=true;
				ib.installForAssets("dispatchplug-debug.apk", "1.1.4", installinfo,new installCallback(){
					@Override
					public void callback(int arg0, Bundle arg1) {
						//Log.e("status改动了一点点",""+arg0);
					}

				});
	        }
	        catch (Exception ex)
	        {
	            System.err.println("Could not create : " + ex);
	            ex.printStackTrace();
	            //int nPid = android.os.Process.myPid();
				//android.os.Process.killProcess(nPid);
	        }
	}
}
