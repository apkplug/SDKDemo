package com.apkplug.easemobplug.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.apkplug.easemobplug.EaseHelper;
import com.apkplug.easemobplug.Processores.EaseContectsProcessor;
import com.apkplug.easemobplug.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.pager_main)
    ViewPager viewPager;
    @Bind(R.id.tab_main)
    TabLayout tabLayout;

    EaseContactListFragment contactListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contactListFragment = new MyEaseContectFragment();

        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,user.getUsername());
                startActivity(intent);
            }
        });

        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.getUserName());
                startActivity(intent);
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(contactListFragment);
        fragments.add(conversationListFragment);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("好友");
        titles.add("会话");
        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }
}
