package com.imedical.mobiledoctor.adapter;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.entity.Item;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder>{

    private List<Item> mList;
    private onRecyclerItemClickListener mRecyclerItemClickListener=null;
    private View view=null;
    private int itemHeight;

    public WorkAdapter(List<Item> mList) {
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_work,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Item item = mList.get(position); // Object Item
        holder.setDesc(item.getName()); // Name
        holder.setImage(item.getIdImage()); // Image
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRecyclerItemClickListener!=null){
                    mRecyclerItemClickListener.onItemClick(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }else {
            return mList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;
        TextView tv_desc;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_icon          = (ImageView)itemView.findViewById(R.id.iv_icon);
            tv_desc          = (TextView)itemView.findViewById(R.id.tv_desc);
            itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    itemHeight=view.getMeasuredHeight();
                    return true;
                }
            });
        }

        public void setDesc(String name){
            tv_desc.setText(name);
        }

        public void setImage(int idImage){
            iv_icon.setImageResource(idImage);
            //FIXME
//            iv_icon.setColorFilter(Color.argb(255,155,155,155), PorterDuff.Mode.SRC_OUT);
        }

    }

    /**
     * item点击接口
     */
    public interface onRecyclerItemClickListener{
        void onItemClick(View view, int position);
    }
    /**
     * 设置点击方法
     */
    public void setOnRecyclerItemClickListener(onRecyclerItemClickListener mRecyclerItemClickListener){
        this.mRecyclerItemClickListener=mRecyclerItemClickListener;
    }

    /**
     *获取高度
     */
    public int getItemHeight(){ return itemHeight;}


}
