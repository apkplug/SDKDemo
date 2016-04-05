package com.apkplugdemo.adapter;
import java.util.List;
import org.apkplug.Bundle.StartActivity;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.apkplug.cloudservicedemo.R;
import com.apkplugdemo.adapter.base.LListAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 显示已安装插件Adapter
 * @author 梁前武 
 * www.apkplug.com
 */
public class UpdataBundleAdapter extends LListAdapter<BundleStutes>{
	public UpdataBundleAdapter(Context c, List<BundleStutes> data) {
		super(c, data);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ListViewHolder viewHolder;
		final Bundle ab=list.get(position).b;
		
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.index_item_listview, null);
			viewHolder = new ListViewHolder();
			viewHolder.imageViewIcon = (ImageView)convertView.findViewById(R.id.image_item_1);
			viewHolder.appName = (TextView)convertView.findViewById(R.id.text_item_1);
			viewHolder.appinfo = (TextView)convertView.findViewById(R.id.text_item_2);
			viewHolder.appSize = (TextView)convertView.findViewById(R.id.text_item_3);
			viewHolder.download = (TextView)convertView.findViewById(R.id.text_item_4);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ListViewHolder)convertView.getTag();
		}
		if(ab.getBundle_icon()!=null){
			viewHolder.imageViewIcon.setImageBitmap(ab.getBundle_icon());
		}
		viewHolder.appName.setText(ab.getName());
		viewHolder.appinfo.setText(ab.getVersion());
		if(list.get(position).ab!=null){
			viewHolder.appSize.
			setText(String.format("%2.2fM", (float)list.get(position).ab.getSize()/(1024*1024)));
		}else{
			viewHolder.appSize.
			setText("");
		}
		if(list.get(position).updatastutes==0){
			viewHolder.download.setText("已是最新版本") ;
		}else{
			viewHolder.download.setText("有更新版本") ;
		}
		
		return convertView;
		}
	
	private final class ListViewHolder {
    	public ImageView imageViewIcon;
    	public TextView appName;
    	public TextView appinfo;
    	public TextView download;
    	public TextView appSize;
    }
	
}
