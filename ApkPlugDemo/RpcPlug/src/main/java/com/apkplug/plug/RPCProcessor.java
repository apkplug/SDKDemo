package com.apkplug.plug;

import android.util.Log;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/9/18.
 */

public class RPCProcessor implements RpcTest {

    @Override
    public void test(String s, IHostBean bean, Action2<String,IPlugBean> callback) {
        Log.e("sss",s);
        Log.e("bean",bean.getHostName());
        callback.call("plugrpc", new IPlugBean() {
            @Override
            public String getName() {
                return "plugbean";
            }
        });
    }
}
