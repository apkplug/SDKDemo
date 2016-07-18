package com.apkplug.www.dispatchplug;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.Processor;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.HashMap;

public class UserInfoProcessor extends Processor {
    private DispatchAgent agent=null;
    public UserInfoProcessor(BundleContext context){
        super(context);
        this.agent=new DispatchAgent(context);
    }
    @Override
    public void Receive(URI uri, HashMap<String, Object> parameter){
        HashMap<String,String> re=new HashMap<>();
        re.put("result_code","success");
        re.put("result_msg","user_id=123456");
        if(parameter.containsKey("user_id")) {
            String user_id = (String) parameter.get("user_id");
            reply("success","user_id:"+user_id);
        }else{
            reply("fail","no user_id");
        }

    }
}
