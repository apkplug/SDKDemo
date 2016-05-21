package com.apkplug.defaultosgiclient;

import org.apkplug.Bundle.ApkplugOSGIService;
import org.apkplug.Bundle.OSGIServiceAgent;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.apkplug.view.myBtn;

public class SimpleBundle implements BundleActivator
{
	private myBtn myBtn=null;
	private ServiceRegistration m_reg = null;
	OSGIServiceAgent<ApkplugOSGIService> agent=null;
    public void start(BundleContext mcontext) 
    {
        System.out.println("Simple Bundle " + mcontext.getBundle().getBundleId()
            + " has started.");
        BundleContextFactory.getInstance().setBundleContext(mcontext);
        //查询主程序提供的showView服务
        myBtn=new myBtn(mcontext.getBundleContext());
        //将BundleContext放入BundleContextFactory工厂中
        agent=new OSGIServiceAgent<ApkplugOSGIService>(mcontext,ApkplugOSGIService.class,
        		"(serviceName=showview)", //服务查询条件
        		OSGIServiceAgent.real_time);   //每次都重新插件
        try {
			agent.getService().ApkplugOSGIService(mcontext, "showview", 0, myBtn);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        
    }
   
    public void stop(BundleContext mcontext)
    {
        System.out.println("Simple Bundle " + mcontext.getBundle().getBundleId()
            + " has stopped.");
        try {
			agent.getService().ApkplugOSGIService(mcontext, "showview", 1, myBtn);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }

}
