package com.apkplug.view;
import org.apkplug.Bundle.StartActivity;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.apkplug.defaultosgiclient.R;
import com.apkplug.defaultosgiclient.testActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class myBtn extends LinearLayout {

	public myBtn(final Context context) {
		super(context);
		//此处必须这样写Context context 只能使用插件自身Context
		LayoutInflater mInflater=LayoutInflater.from(context);
		mInflater = mInflater.cloneInContext(context);
		mInflater.inflate(R.layout.mybtn, this,true); 
		Button button1=(Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent();
				i.setClass(context, testActivity.class);
				context.startActivity(i);
				
			}
		});
	}
	public myBtn(final Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}
