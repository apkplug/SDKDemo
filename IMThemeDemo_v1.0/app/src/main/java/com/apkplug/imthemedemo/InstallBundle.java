package com.apkplug.imthemedemo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apkplug.Bundle.BundleControl;
import org.apkplug.Bundle.OSGIServiceAgent;
import org.apkplug.Bundle.installCallback;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.content.Context;
/**
 * 替代PropertyInstance.AutoStart()的功能
 * @author 梁前武
 */
public class InstallBundle {
	public boolean DEBUG=false;
	OSGIServiceAgent<BundleControl> agent=null;
	public InstallBundle(BundleContext mcontext){
		agent=new OSGIServiceAgent<BundleControl>(mcontext,BundleControl.class);
	}
	private void install(Context context,BundleContext mcontext,String name,installCallback callback,int startlevel,boolean isCheckVersion) throws Exception{
		// startlevel设置为2插件不会自启 isCheckVersion不检测插件版本覆盖更新
		File f1=null;
		try {
				InputStream in=context.getAssets().open(name);
				f1=new File(context.getFilesDir(),name);
				if(!DEBUG){
					//不是调试模式
					if(!f1.exists()){
						copy(in, f1);
						agent.getService().install(mcontext, "file:"+f1.getAbsolutePath(),callback, startlevel,isCheckVersion);
					}
				}else{
					copy(in, f1);
					agent.getService().install(mcontext, "file:"+f1.getAbsolutePath(),callback, startlevel,isCheckVersion);
				}
				
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
		} 
	}
	public void installBundle(Context context,BundleContext mBundleContext,installCallback callback) throws Exception{
				//把BundleDemo1.apk从assets文件夹中移至应用安装目录中
				this.install(context, mBundleContext,"IMTheme.apk", callback,2,false);
				this.install(context, mBundleContext,"IMTheme1.apk", callback,2,false);
	}
	private void copy(InputStream is, File outputFile)
	        throws IOException
	    {
	        OutputStream os = null;

	        try
	        {
	            os = new BufferedOutputStream(
	                new FileOutputStream(outputFile),4096);
	            byte[] b = new byte[4096];
	            int len = 0;
	            while ((len = is.read(b)) != -1)
	                os.write(b, 0, len);
	        }
	        finally
	        {
	            if (is != null) is.close();
	            if (os != null) os.close();
	        }
	    }
	
	
	
}
