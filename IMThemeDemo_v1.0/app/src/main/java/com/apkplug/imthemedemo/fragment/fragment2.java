package com.apkplug.imthemedemo.fragment;
import java.util.ArrayList;
import java.util.List;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

import com.apkplug.imthemedemo.ProxyApplication;
import com.apkplug.imthemedemo.R;
import com.apkplug.imthemedemo.adapter.ListBundleAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class fragment2 extends Fragment{
	private View mMainView;
	private FrameworkInstance frame=null;
	private ListBundleAdapter adapter;
	private ListView bundlelist=null;
    private List<org.osgi.framework.Bundle> bundles=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v("huahua", "fragment2-->onCreate()");
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment2, (ViewGroup)getActivity().findViewById(R.id.viewpager), false);
		frame=((ProxyApplication)this.getActivity().getApplication()).getFrame();
	    bundlelist=(ListView)mMainView.findViewById(R.id.bundlelist);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v("huahua", "fragment2-->onCreateView()");
		
		ViewGroup p = (ViewGroup) mMainView.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        } 
        ListenerBundleEvent();
        initBundleList();
		return mMainView;
	}
	/**
	 * 初始化显示已安装插件的UI
	 */
	public void initBundleList(){
		 //已安装插件列表
		bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
		BundleContext context =frame.getSystemBundleContext();
		for(int i=0;i<context.getBundles().length;i++)
		{
			//获取已安装插件
			//if(context.getBundles()[i].getBundleId()!=0&&!context.getBundles()[i].getSymbolicName().equals("com.example.bundledemotheme"))
				bundles.add(context.getBundles()[i]);        	        
		}
		adapter=new ListBundleAdapter(this.getActivity(),bundles);
		bundlelist.setAdapter(adapter);
	}
	/**
	 * 监听插件安装事件，当有新插件安装或卸载时成功也更新一下
	 */
 	public void ListenerBundleEvent(){
 		frame.getSystemBundleContext()
			.addBundleListener(
					new SynchronousBundleListener(){
	
						public void bundleChanged(BundleEvent event) {
							//把插件列表清空
							bundles.clear();
							BundleContext context =frame.getSystemBundleContext();
							for(int i=0;i<context.getBundles().length;i++)
							{
									bundles.add(context.getBundles()[i]);        	        
	
							}
							adapter.notifyDataSetChanged();
						}
					
			});
	}
}
