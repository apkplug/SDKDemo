package com.apkplug.imthemedemo.activity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apkplug.Bundle.Theme.ApkplugTheme;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.apkplug.imthemedemo.ChatMsgEntity;
import com.apkplug.imthemedemo.R;
import com.apkplug.imthemedemo.ThemeFactory;
import com.apkplug.imthemedemo.R.id;
import com.apkplug.imthemedemo.R.layout;
import com.apkplug.imthemedemo.adapter.ChatMsgViewAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


/**
 * 
 * @author geniuseoe2012
 */
public class ChatActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApkplugTheme cs=ThemeFactory.getInstance(null).getService();
        if(cs!=null){
        	//是否有可替换的主题包
        	int s=cs.getStyles(this.getPackageName(), 100, "chat_activity_styles",0);
        	if(s!=-1){
        		//主题包提供了 chat_activity_styles 这个主题样式
        		this.setTheme(s);
        	}
        }
        setContentView(R.layout.chat_xiaohei);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        initView();
        initData(); 
    }
  
    public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	ApkplugTheme cs=ThemeFactory.getInstance(null).getService();
	    if(cs!=null){
	    	int s=cs.getStyles(this.getPackageName(), 100, "chatbackground",0);
	    	if(s!=-1){
	    		mListView.setBackgroundResource(s);
	    	}
	    }
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);
    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    }
    
    private String[]msgArray = new String[]{"嗨你好", "你好？", "认识", "那上吧", 
    										"去你的", "爆头了",
    										"呵呵", "我去",};
    
    private String[]dataArray = new String[]{"2012-09-01 18:00", "2012-09-01 18:10", 
    										"2012-09-01 18:11", "2012-09-01 18:20", 
    										"2012-09-01 18:30", "2012-09-01 18:35", 
    										"2012-09-01 18:40", "2012-09-01 18:50"}; 
    private final static int COUNT = 8;
    public void initData()
    {
    	for(int i = 0; i < COUNT; i++)
    	{
    		ChatMsgEntity entity = new ChatMsgEntity();
    		entity.setDate(dataArray[i]);
    		if (i % 2 == 0)
    		{
    			entity.setName("小黑");
    			entity.setMsgType(true);
    		}else{
    			entity.setName("人马");
    			entity.setMsgType(false);
    		}
    		
    		entity.setText(msgArray[i]);
    		mDataArrays.add(entity);
    	}

    	mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	private void send()
	{
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("人马");
			entity.setMsgType(false);
			entity.setText(contString);
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			
			mEditTextContent.setText("");
			
			mListView.setSelection(mListView.getCount() - 1);
		}
	}
	
    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        
        
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
        						
        						
        return sbBuffer.toString();
    }
    
    
    public void head_xiaohei(View v) {     //标题栏 返回按钮
    	//Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);			
		//startActivity(intent);	
      } 
}