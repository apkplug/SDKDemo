package cmo.test.mydemo;

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

import org.apkplug.app.FrameworkFactory;
import org.osgi.framework.BundleContext;

import java.util.ArrayList;
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
            PlugManager.getInstance().init(this, FrameworkFactory.getInstance().start(null, this).getSystemBundleContext(), Constants.PUBLICKEY);

            PlugManager.getInstance().requestPermission(this);

            getPlugInfo();

        } catch (Exception e) {
            e.printStackTrace();
        }

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
                                    Intent i=new Intent();
                                    i.setClassName(MainActivity.this, bundle.getBundleActivity().split(",")[0]);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    MainActivity.this.startActivity(i);
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

    void getPlugInfo() {
        mSwipeLayout.setRefreshing(true);
        GetPlugInfoRequest request = new GetPlugInfoRequest();
        request.setNeed_diff(true);
        PlugManager.getInstance().getPlugInfo(request, new OnGetPlugInfoListener() {
            @Override
            public void onSuccess(List<PlugInfo> list) {
                if (list.size() > 0) {
                    plugInfoList = list;
                    for (PlugInfo info : list) {
                        adapter.addData(info.getPlug_name(), 0);
                    }
                    mSwipeLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(String s) {
                textView.setText(s);
                mSwipeLayout.setRefreshing(false);
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
                adapter.setBtnName(plugInfoList.indexOf(bundle.getName()),"运行");
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

                PlugManager.getInstance().updatePlug(this, bundle, new OnUpdataListener() {
                    @Override
                    public void onDownloadProgress(String url, String filePath, long bytesWritten, long totalBytes, PlugInfo plugInfo) {
                        try {
                            PlugDownloadState state = PlugManager.getInstance().queryDownloadState(url);
                            String plugid = state.getPulgId();
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
        PlugManager.getInstance().onRequestPermissionsResult(requestCode, this);
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
}
