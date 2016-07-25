package cmo.test.mydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnInstallListener;
import com.apkplug.trust.common.listeners.OnUpdataListener;
import com.apkplug.trust.data.PlugDownloadState;
import com.apkplug.trust.data.PlugInfo;
import com.apkplug.trust.net.listeners.OnGetPlugInfoListener;
import com.apkplug.trust.net.requests.GetPlugInfoRequest;

import org.apkplug.Bundle.dispatch.DispatchAgent;
import org.apkplug.Bundle.dispatch.WorkerCallback;
import org.apkplug.app.FrameworkFactory;
import org.osgi.framework.BundleContext;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.content)
    TextView textView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.view)
    SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.view2)
    RecyclerView recyclerView;

    MyRecyclerAdapter adapter;
    List<PlugInfo> plugInfoList;
    List<org.osgi.framework.Bundle> bundleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plugInfoList = new ArrayList<>();
        bundleList = new ArrayList<>();

        initview();

        try {
            textView.setText("init");
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null, this).getSystemBundleContext(), Constants.PUBLICKEY,true);

            PlugManager.getInstance().requestPermission(this);



            new Thread(){
                @Override
                public void run() {
                    final org.osgi.framework.Bundle[] bundlef = new org.osgi.framework.Bundle[1];
                    org.osgi.framework.Bundle[] bundles = PlugManager.getInstance().getBundleContext().getBundles();
                    for(org.osgi.framework.Bundle bundle : bundles){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(textView.getText()+"\n查找插件是否安装");
                            }
                        });
                        if(bundle.getName().equals("EasePlug")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(textView.getText()+"\n发现插件已经安装");
                                }
                            });
                            bundlef[0] = bundle;
                            startChat();
                        }
                    }

                    if(bundlef[0]==null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(textView.getText()+"\n安装插件开始");
                            }
                        });
                        PlugManager.getInstance().installAssets("app-debug.apk", "1.0.0", new OnInstallListener() {
                            @Override
                            public void onDownloadProgress(String url, String filePath, long bytesWritten, long totalBytes, PlugInfo plugInfo) {

                            }

                            @Override
                            public void onInstallSuccess(final org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView.setText(textView.getText()+"\n插件安装成功");
                                    }
                                });
                                startChat();
                            }

                            @Override
                            public void onInstallFailuer(int i, PlugInfo plugInfo, String errorMsg) {

                            }

                            @Override
                            public void onDownloadFailure(String errorMsg) {

                            }
                        });
                    }

                }
            }.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startChat() {

        HashMap<String,Object> params3 = new HashMap<String, Object>();
        params3.put("auto_access",true);
        params3.put("debug",true);

        final DispatchAgent dispatchAgent=new DispatchAgent(PlugManager.getInstance().getBundleContext());

        HashMap<String,Object> params2 = new HashMap<String, Object>();
        params2.put("UserName","apkplug");
        params2.put("Password","lbh131206");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(textView.getText()+"\n开始登陆环信");
            }
        });
        dispatchAgent.call("http://apkplug.plug.com/meseplug/login", params2, new WorkerCallback() {
            @Override
            public void reply(URI uri, Object... objects) throws Exception {

                if(!(Boolean) objects[0]){
                    return;
                }
                textView.setText(textView.getText()+"\n环信登陆成功");
                chatInitandLogin(dispatchAgent);

            }

            @Override
            public void timeout(URI uri) throws Exception {

            }

            @Override
            public void Exception(URI uri, Throwable throwable) {
                System.err.println(uri);
            }
        });
    }

    private void chatInitandLogin(final DispatchAgent dispatchAgent) {
        textView.setText(textView.getText()+"\n获取好友列表");
        dispatchAgent.call("http://apkplug.plug.com/meseplug/contect", null, new WorkerCallback() {
            @Override
            public void reply(URI uri, Object... objects) throws Exception {
                try {
                    if((Boolean)objects[0] && !((List<String>)objects[1]).contains("apkplug4")){
                        HashMap<String,Object> params = new HashMap<String, Object>();
                        params.put("UserName","apkplug4");
                        textView.setText(textView.getText()+"\n添加好友");
                        dispatchAgent.call("http://apkplug.plug.com/meseplug/addfriend", params, new WorkerCallback() {
                            @Override
                            public void reply(URI uri, Object... objects) throws Exception {
                                if((Boolean) objects[0]){
                                    Log.e("addf","ssss");
                                    textView.setText(textView.getText()+"\n添加成功");
                                }
                            }

                            @Override
                            public void timeout(URI uri) throws Exception {

                            }

                            @Override
                            public void Exception(URI uri, Throwable throwable) {

                            }
                        });
                    }

                    startChatActivity("com.apkplug.easemobplug.ui.LoginActivity");

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void timeout(URI uri) throws Exception {

            }

            @Override
            public void Exception(URI uri, Throwable throwable) {
                System.err.println(uri);
            }
        });
    }

    private void startChatActivity(String className) {
        textView.setText(textView.getText()+"\n启动环信界面");
        Intent intent = new Intent();
        intent.setClassName(MainActivity.this, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_show_download) {
            Intent intent = new Intent(this, DownloadPanel.class);
            startActivity(intent);
        }
        if (id == R.id.update) {
            updataLocalPlug();
        }

        return super.onOptionsItemSelected(item);
    }

    void initview() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mSwipeLayout.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(false);
            }
        });

        adapter = new MyRecyclerAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setRecyclerItemClickListener(new MyRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onClick(View view, int person) {
                String btnName = ((Button)view).getText().toString();
                if(btnName.equals("下载")){
                    try {
                        installplug(plugInfoList.get(person));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(btnName.equals("运行")){
                    for(org.osgi.framework.Bundle bundle : bundleList){
                        if(bundle.getName().equals(plugInfoList.get(person).getPlug_name())){
                            try {
                                if(bundle.getState()!=bundle.ACTIVE){
                                    //判断插件是否已启动
                                    try {
                                        bundle.start();
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                                if(bundle.getBundleActivity()!=null){
                                    Toast.makeText(MainActivity.this, "启动"+bundle.getBundleActivity().split(",")[0],
                                            Toast.LENGTH_SHORT).show();
                                    startChatActivity(bundle.getBundleActivity().split(",")[0]);
                                }else{

                                    Toast.makeText(MainActivity.this, "该插件没有配置BundleActivity",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });

    }


    void installplug(PlugInfo info) {
        PlugManager.getInstance().installPlug(MainActivity.this, info, new OnInstallListener() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1, PlugInfo plugInfo) {
                try {
                    PlugDownloadState state = PlugManager.getInstance().queryDownloadState(s);
                    String plugid = state.getPlug_name();
                    float percent = (float) l / (float) l1;
                    adapter.setData(adapter.getOldValue(plugid), plugid + String.format("\n 下载：%d / %d", l, l1), percent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                if (bundle != null) {
                    Log.e("install", " bundle" + bundle.getName());
                }
                bundleList.add(bundle);
                int i = plugInfoList.indexOf(plugInfo);
                adapter.setBtnName(i,"运行");
            }

            @Override
            public void onInstallFailuer(int i, PlugInfo plugInfo, String errorMsg) {
                Log.e("install failuer", errorMsg);
                Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDownloadFailure(String s) {
                Log.e("install f","download failure");
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }
        });
    }


    void updataLocalPlug() {
        try {
            BundleContext bundleContext = FrameworkFactory.getInstance().start(null, this).getSystemBundleContext();
            org.osgi.framework.Bundle[] bundles = bundleContext.getBundles();
            for (org.osgi.framework.Bundle bundle : bundles) {

                PlugManager.getInstance().updatePlug(bundle, new OnUpdataListener() {
                    @Override
                    public void onDownloadProgress(String url, String filePath, long bytesWritten, long totalBytes, PlugInfo plugInfo) {
                        try {
                            PlugDownloadState state = PlugManager.getInstance().queryDownloadState(url);
                            String plugid = state.getPlug_name();
                            float percent = (float) bytesWritten / (float) totalBytes;
//                                System.out.println("float: "+percent);
                            adapter.setData(adapter.getOldValue(plugid), plugid + String.format("\n 下载：%d / %d", bytesWritten, totalBytes), percent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onUpdataSuccess(org.osgi.framework.Bundle bundle, PlugInfo plugInfo) {
                        if (bundle != null) {
                            Log.e("", bundle.getName());
                            textView.setText(bundle.getName());
                        }
                    }

                    @Override
                    public void onUpdataFailuer(int i, PlugInfo plugInfo, String errorMsg) {
                        textView.setText(errorMsg);
                    }

                    @Override
                    public void onDownloadFailure(String errorMsg) {
                        Log.e("", errorMsg);
                        textView.setText(errorMsg);
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PlugManager.getInstance().onRequestPermissionsResult(requestCode, this,permissions,grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() != KeyEvent.ACTION_UP) {
            AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
            alertbBuilder
                    .setTitle("真的要退出？")
                    .setMessage("你确定要退出？")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    //MyProperty中调试模式设置为true调用shutdown将清理已安装插件缓存
                                    //以在下次启动时重新安装
                                    int nPid = android.os.Process.myPid();
                                    android.os.Process.killProcess(nPid);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            }).create();
            alertbBuilder.show();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}
