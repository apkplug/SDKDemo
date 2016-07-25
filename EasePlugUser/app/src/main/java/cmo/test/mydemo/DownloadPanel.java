package cmo.test.mydemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.data.PlugDownloadState;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DownloadPanel extends AppCompatActivity {

    @Bind(R.id.view2)
    RecyclerView recyclerView;

    MyRecyclerAdapter adapter;

    Handler mHandler;

    boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mHandler = new Handler();

        try {

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new MyRecyclerAdapter(this);
            adapter.isShowbtn(false);
            recyclerView.setAdapter(adapter);

            new Thread(){
                @Override
                public void run() {
                    super.run();

                    while (!stop) {

                        final Set<PlugDownloadState> states = PlugManager.getInstance().getAllDownloadStates();

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                for (final PlugDownloadState state : states) {
                                    final float percent = (float) state.getByteWritted() / (float) state.getTotalByte();
                                    if(adapter.getOldValue(state.getPulgId()) == null){
                                        adapter.addData(state.getPlug_name(), percent);
                                    }else {
                                        adapter.setData(state.getPlug_name(),state.getPulgId(),percent);
                                    }

                                }
                            }
                        });

                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
