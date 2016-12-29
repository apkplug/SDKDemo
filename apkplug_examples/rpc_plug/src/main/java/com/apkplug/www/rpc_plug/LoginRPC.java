package com.apkplug.www.rpc_plug;

import android.content.Intent;

import com.apkplug.www.rpc_plug.rpc.Login;
import com.apkplug.www.rpc_plug.rpc.LoginCallback;
import com.apkplug.www.rpc_plug.rpc.LogoutCallback;
import com.apkplug.www.rpc_plug.rpc.UserBean;
import org.apkplug.Bundle.bundlerpc.ObjectPool;
/**
 *apkplug://rpc/login 服务提供类
 */
public class LoginRPC implements Login {

    @Override
    public void login(String user_id, String pwd, LoginCallback callback) {
        //这个类里面没有办法直接处理登陆,需要跳转到LoginActivity让用户输入用户名,密码,这也是一般第三方登陆SDK的基本逻辑
        Intent intent=new Intent();
        //当宿主调用这个rpc时 框架会先启动插件,因此可以用个SimpleBundle得到BundleContext 进而得到一个Context
        intent.setClass(SimpleBundle.mcontext.getAndroidContext(),LoginActivity.class);

        //LoginActivity里面登陆成功以后需要调用LoginCallback回调到宿主,我们通过ObjectPool 将一个不能序列化的LoginCallback传递到LoginActivity
        ObjectPool pool=new ObjectPool(callback);
        intent.putExtra("callback",pool);

        SimpleBundle.mcontext.getAndroidContext().startActivity(intent);

    }

    @Override
    public void logout(UserBean user, LogoutCallback callback) {

    }
}
