package com.apkplug.imthemedemo;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apkplug.app.PropertyInstance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

public class MyProperty implements PropertyInstance{
	private   Context context;
	private static MyProperty _instance=null;
	public MyProperty(Context context){
		this.context=context;
		this.setProperty(Start_Service_for_Init, "true");
	}
	synchronized public static MyProperty getInstance(Context context){
    if(_instance==null){
    _instance=new MyProperty(context);
    }
    return _instance;
    } 

	public String getProperty(String key) {
		// TODO Auto-generated method stub
		SharedPreferences sharedata = PreferenceManager.getDefaultSharedPreferences(this.context);
		String data = sharedata.getString(key, null);
		return data;
	}
	public void setProperty(String key, String v) {
		// TODO Auto-generated method stub
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.context); 
		Editor edit = settings.edit();
		edit.putString(key, v);
		edit.commit();
	}
	public String[] AutoInstall() {
		// TODO Auto-generated method stub
		return null;
	}
	public String[] AutoStart() {
		return null;
	}

	@Override
	public boolean Debug() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
