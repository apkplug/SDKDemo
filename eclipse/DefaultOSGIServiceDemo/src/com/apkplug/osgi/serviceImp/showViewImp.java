package com.apkplug.osgi.serviceImp;

import org.apkplug.Bundle.ApkplugOSGIService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import android.view.View;
import android.widget.LinearLayout;

public class showViewImp implements ApkplugOSGIService{
	private LinearLayout layout =null;
	/**
	 * @param root 插件 View保存UI容器 
	 */
	public showViewImp(View root){
		this.layout=(LinearLayout)root;
	}
	@Override
	public Object ApkplugOSGIService(BundleContext arg0, String servicename, int type,Object... views) {
		if(type==0){
			System.out.println("插件传来一个View");
			layout.addView((View)views[0]);
		}else if(type==1){
			System.out.println("插件需要删除一个View");
			layout.removeView((View)views[0]);
		}
		return true;
	}

}
