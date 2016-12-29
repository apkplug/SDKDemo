package com.apkplug.www.rpc_host.rpc;

/**
 * Created by love on 16/9/21.
 */
public interface LoginCallback {
    public void onSuccess(String platform, String uid, String uname);
    public void onFail(String status, String errorMsg);
}
