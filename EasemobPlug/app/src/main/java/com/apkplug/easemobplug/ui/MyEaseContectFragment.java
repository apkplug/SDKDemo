package com.apkplug.easemobplug.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.apkplug.easemobplug.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinfeng on 2016/7/22.
 */
public class MyEaseContectFragment extends EaseContactListFragment {
    Handler handler = new Handler(Looper.myLooper());
    @Override
    protected void setUpView() {

        new Thread(){
            @Override
            public void run() {
                List<String> allFirends = null;
                try {
                    allFirends = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    System.err.println(allFirends.get(0));
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    System.err.println("get all friends fail"+e);
                }

                Map<String, EaseUser> userlist = new HashMap<String, EaseUser>();
                if(allFirends != null){
                    for (String username : allFirends) {
                        EaseUser user = new EaseUser(username);
                        EaseCommonUtils.setUserInitialLetter(user);
                        userlist.put(username, user);
                    }
                }

                setContactsMap(userlist);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyEaseContectFragment.super.setUpView();
                    }
                });
            }
        }.start();

        titleBar.setRightImageResource(R.drawable.em_add);
        titleBar.getRightLayout().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });


    }


}
