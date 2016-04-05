package com.apkplug.cloudservicedemo.activity;

import java.util.ArrayList;
import java.util.List;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.apkplug.CloudService.*;
import com.apkplug.CloudService.SearchApp.AppSearchCallBack;
import com.apkplug.CloudService.Bean.AppSearchBean;
import com.apkplug.CloudService.Model.AppQueryModel;
import com.apkplug.CloudService.Model.AppModel;
import com.apkplug.cloudservicedemo.ProxyApplication;
import com.apkplug.cloudservicedemo.R;
import com.apkplugdemo.adapter.SearchBundleAdapter;
import android.os.Bundle;
import android.app.ListActivity;
import android.widget.Toast;

public class SearchActivity extends ListActivity {
	private FrameworkInstance frame=null;
	private AppSearchBean bean=null;
	private SearchBundleAdapter adapter;

    private ArrayList<AppModel> apps = null;

    /** Called when the activity is first created. */
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        frame=((ProxyApplication)this.getApplication()).getFrame();
        apps = new ArrayList<AppModel>();
        updataDate(frame.getSystemBundleContext());
        adapter = new SearchBundleAdapter(SearchActivity.this,frame.getSystemBundleContext(),apps);
        setListAdapter(adapter);
    }
	public void updataDate(BundleContext context){
		AppSearchBean bean=new AppSearchBean();
    	bean.setPagenum(10);
    	bean.setPage(0);
	    //查询
    	ApkplugCloudAgent.getInstance(null).getAppSearch().search(bean,new impAppSearchCallBack());
	}
	
	/**
	 * 查询回调接口
	 * @author 梁前武
	 *
	 */
   class impAppSearchCallBack implements AppSearchCallBack{
	   public void onFailure(int arg0, final String arg1) {
		   //服务查询失败
			SearchActivity.this.runOnUiThread(new Runnable(){
				public void run() {
					Toast.makeText(SearchActivity.this, arg1,
				     Toast.LENGTH_SHORT).show();
				}
			});	
		}
	   @Override
		public void onSuccess(int arg0, AppQueryModel<AppModel> arg1) {
		   System.out.println("查询成功");
			for(int i=0;i<arg1.getData().size();i++){
				System.out.println(arg1.getData().get(i).getAppname());
			}
			for (int i = 0; i < arg1.getData().size(); i++) {
				AppModel ab=arg1.getData().get(i);
				apps.add(ab);
			 }
			//更新列表
			SearchActivity.this.runOnUiThread(new Runnable(){
			 		public void run(){
					 adapter.notifyDataSetChanged();
			 		}
			});
		}  
		
		
   }

}
