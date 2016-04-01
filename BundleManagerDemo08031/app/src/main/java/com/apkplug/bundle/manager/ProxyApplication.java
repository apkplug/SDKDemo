package com.apkplug.bundle.manager;
import org.apkplug.Bundle.InstallBundler;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.apkplug.mxdstream.streamip.RegStreamIPAgent;
import org.apkplug.mxdstream.streamip.rever.StreamIPReverTest;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ProxyApplication extends Application {

	private FrameworkInstance frame=null;
	
	public FrameworkInstance getFrame() {
		return frame;
	}

	public void onCreate() {   
		
		Log.e("ProxyApplication", this.getApplicationContext().getApplicationInfo().sourceDir);
		 super.onCreate(); 
		 try
	        {
	        	//启动框架
				//文档见 http://www.apkplug.com/javadoc/Maindoc1.4.6/
				//org.apkplug.app 
			 	//     接口 FrameworkInstance
			 	Log.e("start", "fsfsdafasfsa");
				frame=FrameworkFactory.getInstance().start(null, this);
				//frame=FrameworkFactory.getInstance().start(null,this);
				System.out.println("ProxyApplication1");
				BundleContext context =frame.getSystemBundleContext();
				// InstallBundler 是2.7.0版本内置与框架中的
				InstallBundler ib=new InstallBundler(context);
				ib.installForAssets("BundleDemoJni.apk", "1.0.0", null,
						new installCallback(){
						@Override
						public void callback(int arg0, Bundle arg1) {
							if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
								Log.d("",String.format("插件安装 %s ： %d",arg1.getName(),arg0));
								return;
							}
							else{
								Log.d("","插件安装失败 ：%d"+arg0);
							}
						}
						});
				ib.installForAssets("BundleDemoShow.apk", "1.0.0", null,null);
				ib.installForAssets("BundleDemoStartActivity1.apk", "1.0.0", null,null);
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
