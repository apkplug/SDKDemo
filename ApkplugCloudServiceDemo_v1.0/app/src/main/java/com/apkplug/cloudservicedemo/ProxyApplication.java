package com.apkplug.cloudservicedemo;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import com.apkplug.CloudService.ApkplugCloudAgent;

import android.app.Application;

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
				//启动云服务包括插件搜索 下载 更新功能
				ApkplugCloudAgent.getInstance(context).init();
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
