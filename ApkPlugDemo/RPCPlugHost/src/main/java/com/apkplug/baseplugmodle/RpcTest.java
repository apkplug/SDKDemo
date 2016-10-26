package com.apkplug.baseplugmodle;

import org.apkplug.Bundle.bundlerpc.functions.Action2;

/**
 * Created by qinfeng on 2016/9/18.
 */

public interface RpcTest {
    void test(String s,IHostBean bean,Action2<String,IPlugBean> callback);
}
