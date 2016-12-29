package com.apkplug.www.rpc_host.rpc;

public interface Login {
    public void login(String user_id, String pwd, LoginCallback callback);

    public void logout(UserBean user, LogoutCallback callback);
}
