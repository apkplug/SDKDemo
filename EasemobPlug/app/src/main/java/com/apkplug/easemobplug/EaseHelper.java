package com.apkplug.easemobplug;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;

/**
 * Created by qinfeng on 2016/7/19.
 */
public class EaseHelper {
    public static final String TAG = "EaseHelper";
    private static EaseHelper mInstance;

    private HashMap<String,EaseUser> contects;

    private EaseHelper(){
        contects = new HashMap<>();
    }

    public static EaseHelper getInstance(){
        if(mInstance == null){
            synchronized (EaseHelper.class){
                if(mInstance == null){
                    mInstance = new EaseHelper();
                }
            }
        }
        return mInstance;
    }

    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    public void setContects(HashMap<String,EaseUser> contects){
        this.contects = contects;
    }

    public HashMap<String,EaseUser> getContects(){
        return contects;
    }

    public void addContact(String key,String userName){
        EaseUser user = new EaseUser(userName);
        contects.put(key,user);
    }

    public String getContect(String key){
        EaseUser user = contects.get(key);
        if(user == null){
            return null;
        }
        return user.getUsername();
    }

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
