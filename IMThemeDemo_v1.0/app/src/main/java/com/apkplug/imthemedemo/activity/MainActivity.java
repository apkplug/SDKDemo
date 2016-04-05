package com.apkplug.imthemedemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apkplug.Bundle.OSGIServiceAgent;
import org.apkplug.Bundle.Theme.OnThemeChengeListener;
import org.apkplug.Bundle.Theme.RegThemeChengeListener;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.apkplug.imthemedemo.ProxyApplication;
import com.apkplug.imthemedemo.R;
import com.apkplug.imthemedemo.R.id;
import com.apkplug.imthemedemo.R.layout;
import com.apkplug.imthemedemo.ThemeFactory;
import com.apkplug.imthemedemo.fragment.fragment1;
import com.apkplug.imthemedemo.fragment.fragment2;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {
	private ViewPager m_vp;
	private fragment1 mfragment1;
	private fragment2 mfragment2;
	//页面列表
	private ArrayList<Fragment> fragmentList;
	//标题列表
	ArrayList<String>   titleList    = new ArrayList<String>();
	//通过pagerTabStrip可以设置标题的属性
	private PagerTabStrip pagerTabStrip;
	private FrameworkInstance frame=null;
	private PagerTitleStrip pagerTitleStrip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		m_vp = (ViewPager)findViewById(R.id.viewpager);
		frame=((ProxyApplication)this.getApplication()).getFrame();
		pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
		//设置下划线的颜色
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_dark)); 
		//设置背景的颜色
		pagerTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
//		pagerTitleStrip=(PagerTitleStrip) findViewById(R.id.pagertab);
//		//设置背景的颜色
//		pagerTitleStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
		
		mfragment1 = new fragment1();
		mfragment2 = new fragment2();

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(mfragment1);
		fragmentList.add(mfragment2);
		
	    titleList.add("聊天记录 ");
	    titleList.add("已有插件");
	    titleList.add("插件商城 ");
	    try {
			ListenerTheme();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		m_vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
//		m_vp.setOffscreenPageLimit(2);
	}
	public void ListenerTheme() throws Exception{
		BundleContext context=frame.getSystemBundleContext();
		//RegThemeChengeListener 服务
    	final RegThemeChengeListener service=ThemeFactory.getInstance(context).getRegThemeChengeListener();
    		if(service!=null){
    			//插件启动级别为1(会自启) 并且不检查插件版本是否相同都安装
    			service.registerOnThemeListener(
    					new OnThemeChengeListener(){
							@SuppressLint("NewApi")
							@Override
							public void afterChenged(org.osgi.framework.Bundle arg0, int arg1) {
								//主题切换了刷新界面
								MainActivity.this.recreate();
								//监听完注销该监听器,因为界面重刷以后会注册新的
								service.unregisterOnThemeListener(this);
							}
							@Override
							public void beforeChenge(org.osgi.framework.Bundle bb, int arg1,org.osgi.framework.Bundle tob, int arg2) {
								
							}
    			});
    		}
	}
	
	public void startchat(View v) {      //小黑  对话界面
		Intent intent = new Intent (MainActivity.this,ChatActivity.class);			
		startActivity(intent);	
		//Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
      }  
	public class MyViewPagerAdapter extends FragmentPagerAdapter{
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titleList.get(position);
		}

		
	}

}
