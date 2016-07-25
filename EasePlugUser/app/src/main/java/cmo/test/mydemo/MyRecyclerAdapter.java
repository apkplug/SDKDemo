package cmo.test.mydemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by libohan on 2016/4/23.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> mData;
    private List<Float> mPercent;
    private boolean showbtn = true;
    private Map<Integer,String> btnNames;
    public MyRecyclerAdapter(Context context){
        this.context = context;
        mData = new ArrayList<>();
        mPercent = new ArrayList<>();
        btnNames = new HashMap<>();
    }

    public void addData(String data,float percent){
        if(getOldValue(data) != null){
            setData(getOldValue(data),data,percent);
            return;
        }
        mData.add(data);
        mPercent.add(percent);
       // notifyItemChanged(mData.indexOf(data));
        notifyDataSetChanged();
    }


    public void setData(int index,String data,float percent){
        mData.set(index, data);
        mPercent.set(index,percent);
        notifyDataSetChanged();
    }

    public void setData(String oldValue,String newValue,float percent){
        int index = mData.indexOf(oldValue);
        mData.set(index,newValue);
        mPercent.set(index,percent);

        notifyDataSetChanged();
    }

    public String getOldValue(String value){
        for(String oldv : mData){
            if(oldv.startsWith(value)){
                return oldv;
            }
        }
        return null;
    }

    public void setBtnName(int key,String btnName){
        btnNames.put(key,btnName);
        notifyDataSetChanged();
    }

    public void isShowbtn(boolean showbtn){
        this.showbtn = showbtn;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerviewitem,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        if(listener != null) {
            viewHolder.button.setOnClickListener(this);
        }
        if(!showbtn){
            viewHolder.button.setVisibility(View.GONE);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(mData.get(position));
        int progress = (int) (mPercent.get(position)*100f);
        holder.progressBar.setProgress(progress);
        if(mPercent.get(position)>0){
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        holder.button.setTag(position);
        if(btnNames.get(position)!=null) {
            holder.button.setText(btnNames.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,(int)v.getTag());
    }

    public interface OnRecyclerItemClickListener{
        void onClick(View view,int person);
    }

    private OnRecyclerItemClickListener listener;

    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener){
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ProgressBar progressBar;
        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            button = (Button) itemView.findViewById(R.id.btn_download);
        }
    }
}
