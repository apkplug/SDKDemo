package com.apkplug.easemobplug.ui;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.apkplug.easemobplug.R;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qinfeng on 2016/7/20.
 */
public class ChatActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatactivity);
        ButterKnife.bind(this);

        EaseChatFragment chatFragment = new EaseChatFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        String user = (String) getIntent().getExtras().get(EaseConstant.EXTRA_USER_ID);
        args.putString(EaseConstant.EXTRA_USER_ID, user);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chatcontainer, chatFragment).commit();

    }
}
