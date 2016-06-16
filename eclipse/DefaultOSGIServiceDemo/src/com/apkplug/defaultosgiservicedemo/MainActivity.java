package com.apkplug.defaultosgiservicedemo;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apkplug.Bundle.ApkplugOSGIService;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.apkplug.adapter.ListBundleAdapter;
import com.apkplug.osgi.serviceImp.showViewImp;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private FrameworkInstance frame=null;
	private List<org.osgi.framework.Bundle> bundles=null;
	private ListView bundlelist =null;
	private ListBundleAdapter adapter=null;
	private showViewImp showview=null;
	private ServiceRegistration m_reg = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			frame=FrameworkFactory.getInstance().start(null,this);
			BundleContext context =frame.getSystemBundleContext();
			InstallBundle ib=new InstallBundle(context);
			ib.install(context, "DefaultOSGIClient.apk", "1.0.0", null, 2, false);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		TextView info =(TextView)this.findViewById(R.id.info);
		
		LinearLayout layout=(LinearLayout)this.findViewById(R.id.layout);
		showview=new showViewImp(layout);
		//定义服务查询条件
		Dictionary<String,Object> properties =new Hashtable<String,Object>();
    	properties.put("serviceName", "showview");
		//注册一个服务给插件调用
		m_reg = frame.getSystemBundleContext().registerService(
				ApkplugOSGIService.class.getName(),
				showview,
				properties);
		//加载插件
		initBundleList();
	}

	/**
	 * 初始化显示已安装插件的UI
	 */
	public void initBundleList(){
		 //已安装插件列表
        bundlelist=(ListView)findViewById(R.id.bundlelist);
		bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
		BundleContext context =frame.getSystemBundleContext();
		for(int i=0;i<context.getBundles().length;i++)
		{
			//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
		}
		adapter=new ListBundleAdapter(MainActivity.this,bundles);
		bundlelist.setAdapter(adapter);
	}
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() != KeyEvent.ACTION_UP) {
			AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
			alertbBuilder
					.setTitle("真的要退出？")
					.setMessage("你确定要退出？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									int nPid = android.os.Process.myPid();
									android.os.Process.killProcess(nPid);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).create();
			alertbBuilder.show();
			return true;
		} else {
			return super.dispatchKeyEvent(event);
		}
	} 
}
