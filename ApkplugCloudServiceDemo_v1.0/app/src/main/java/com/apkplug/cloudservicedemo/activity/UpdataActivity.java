package com.apkplug.cloudservicedemo.activity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apkplug.CloudService.ApkplugCloudAgent;
import com.apkplug.CloudService.Bean.UpdateAppBean;
import com.apkplug.CloudService.Bean.UpdateAppInfo;
import com.apkplug.CloudService.Model.AppModel;
import com.apkplug.CloudService.Model.AppQueryModel;
import com.apkplug.CloudService.Update.VersionCallBack;
import com.apkplug.cloudservicedemo.ProxyApplication;
import com.apkplug.cloudservicedemo.R;
import com.apkplugdemo.adapter.BundleStutes;
import com.apkplugdemo.adapter.UpdataBundleAdapter;

import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import java.util.List;
/**
 * 0.0.1 版本新增安装本地插件接口
 * MainActivity.install(String path,installCallback callback)
 * @author 梁前武 QQ 1587790525
 * www.apkplug.com
 */
public class UpdataActivity extends Activity {
	@Override
	protected void onRestart() {
		frame=((ProxyApplication)this.getApplication()).getFrame();
		super.onRestart();
	}


	private FrameworkInstance frame=null;
	private List<BundleStutes> bundles=null;
	private ListView bundlelist =null;
	private UpdataBundleAdapter adapter=null;
	private TextView info=null;
	public Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		frame=((ProxyApplication)this.getApplication()).getFrame();
		info =(TextView)this.findViewById(R.id.info);
		initBundleList();
	}

	/**
	 * 初始化显示已安装插件的UI
	 */
	public void initBundleList(){
		 //已安装插件列表
        bundlelist=(ListView)findViewById(R.id.bundlelist);
		bundles=new java.util.ArrayList<BundleStutes>();

		BundleContext context =frame.getSystemBundleContext();
		UpdateAppBean bean=new UpdateAppBean();
		List<UpdateAppInfo> l=new java.util.ArrayList<UpdateAppInfo>();
		org.osgi.framework.Bundle[] contextBundles = context.getBundles();
		for(int i=0;i<contextBundles.length;i++)
		{		
				BundleStutes bs=new BundleStutes();
				bs.b=context.getBundles()[i];
				bs.updatastutes=0;
				bs.appid=context.getBundles()[i].getSymbolicName();
				bundles.add(bs);   
				//填充更新查询字段
				org.osgi.framework.Bundle bundle = contextBundles[i];
				if(bundle != null)
					l.add(UpdateAppInfo.createUpdateAppInfo(bundle));
		}
		adapter=new UpdataBundleAdapter(UpdataActivity.this,bundles);
		bundlelist.setAdapter(adapter);
		bean.setApps(l);
		//从云服务器查询所有已安装插件版本状态
		ApkplugCloudAgent.getInstance(null).getcheckupdate().checkupdate(bean,new impupdateCallBack());
	}
	/**
	 * 通过appid匹配已安装插件
	 * @param appid
	 * @return
	 */
	public BundleStutes getBundleByAppid(String appid){
		for(int i=0;i<bundles.size();i++){
			if(bundles.get(i).appid.equals(appid)){
				//查找到
				return bundles.get(i);
			}
		}
		return null;
	} 

	/**
	 * 插件版本更新OSGI服务回调函数
	 * @author 梁前武
	 *
	 */
	class impupdateCallBack implements VersionCallBack{
		@Override
		public void onFailure(int arg0, final String arg1) {
			UpdataActivity.this.runOnUiThread(new Runnable(){
				@Override
				public void run() {
					info.setText("查询插件版本状态出现异常:\n\r"+arg1);
					Toast.makeText(UpdataActivity.this, "查询插件版本状态出现异常:\n\r"+arg1,
						     Toast.LENGTH_SHORT).show();
				}
			});
		}
		@Override
		public void onSuccess(int arg0, final AppQueryModel<AppModel> arg1) {
						// arg1 有更新版本的插件appbean arg2 插件服务状态信息
						UpdataActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								Toast.makeText(UpdataActivity.this, "查询插件版本状态完成:\n\r有新版本插件数量为:"+arg1.getData().size(),
									     Toast.LENGTH_SHORT).show();
								info.setText("查询插件版本状态完成:\n\r有新版本插件数量为:"+arg1.getData().size());
							}
						});
						for(int i=0;i<arg1.getData().size();i++){
							AppModel a=arg1.getData().get(i);
							BundleStutes bs=getBundleByAppid(a.getAppid());
							if(bs!=null){
								//匹配到插件,并更新其状态
								bs.updatastutes=1;
								bs.ab=a;
							}
						}
						//刷新列表
						UpdataActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								adapter.notifyDataSetChanged();
							}
						});
		}
		
		
	}
 	
}
