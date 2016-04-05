package com.apkplug.cloudservicedemo.activity;

import java.util.Arrays;
import java.util.ArrayList;

import com.apkplug.cloudservicedemo.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.DialogInterface;
import android.content.Intent;

//import com.mobeta.android.demodslv.R;


public class Launcher extends ListActivity {

    //private ArrayAdapter<ActivityInfo> adapter;
    private MyAdapter adapter;

    private ArrayList<String> mActivities = null;

    private String[] mActTitles;
    private String[] mActDescs;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        mActivities = new ArrayList<String>();
        mActivities.add("com.apkplug.cloudservicedemo.activity.SearchActivity");
        mActivities.add("com.apkplug.cloudservicedemo.activity.UpdataActivity");
      

        mActTitles = getResources().getStringArray(R.array.activity_titles);
        mActDescs = getResources().getStringArray(R.array.activity_descs);

        //adapter = new ArrayAdapter<ActivityInfo>(this,
        //  R.layout.launcher_item, R.id.text, mActivities);
        adapter = new MyAdapter();

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
	    Intent intent = new Intent();
	    intent.setClassName(this, mActivities.get(position));
	    startActivity(intent);
    }

    private class MyAdapter extends ArrayAdapter<String> {
      MyAdapter() {
        super(Launcher.this, R.layout.launcher_item, R.id.activity_title, mActivities);
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        TextView title = (TextView) v.findViewById(R.id.activity_title);
        TextView desc = (TextView) v.findViewById(R.id.activity_desc);

        title.setText(mActTitles[position]);
        desc.setText(mActDescs[position]);
        return v;
      }

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
