package com.imedical.im.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.imedical.im.activity.ReservationListActivity;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.entity.CanUseRes;
import com.imedical.mobiledoctor.entity.ServiceOrder;

public class Adapter_ReserDate extends BaseAdapter {
    ReservationListActivity ctx=null;
    public Adapter_ReserDate(ReservationListActivity activity) {
        this.ctx=activity;
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    private int mCurrentItem=0;

    @Override
    public int getCount() {
        return ctx.list_date.size();
    }

    @Override
    public Object getItem(int position) {
        return ctx.list_date.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
      final CanUseRes CUR = ctx.list_date.get(position);
      final   ViewHolder holder;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_reser_date, null);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mCurrentItem == position){
            holder.tv_date.setBackground(ctx.getResources().getDrawable(R.drawable.bg_shape_videobutton_2));
        }else {
            holder.tv_date.setBackground(ctx.getResources().getDrawable(R.drawable.bg_shape_videobutton));
        }
        holder.tv_date.setText(CUR.bookedDate);
        return convertView;
    }

    public void setCurrentItem(int currentItem) {
        this.mCurrentItem = currentItem;
    }


    public class ViewHolder {
        public TextView tv_date;
    }
}
