package com.imedical.mobiledoctor.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.round.detail.LisHis_DetailActivity;
import com.imedical.mobiledoctor.entity.LisReportItem;

public class AdapterLisCompare extends BaseAdapter {
    private LisHis_DetailActivity ctx;
    public AdapterLisCompare(LisHis_DetailActivity activity) {
        ctx=activity;
    }

    @Override
    public int getCount() {
        return ctx.mDataListLisCompare.size();
    }

    @Override
    public Object getItem(int position) {
        return ctx.mDataListLisCompare.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LisReportItem lis = ctx.mDataListLisCompare.get(position);
        ViewHolder holder;
//			if (convertView == null) {
        holder = new ViewHolder();
        convertView = LayoutInflater.from(ctx).inflate(R.layout.lis_compare_item, null);
        holder.layout = (LinearLayout)convertView.findViewById(R.id.item_layout);
        TextView tv_desc = createItemTextView(lis.itemDesc);
        tv_desc.setTextColor(Color.BLACK);
        holder.layout.addView(tv_desc);
        //根据日期填充数据
        for (int i = 0,j = 0; i < ctx.dateList.size()   && j < lis.resultList.size();) {
            if (lis.resultList.get(j).reportDate.equals(ctx.dateList.get(i))) {
                String txt = lis.resultList.get(j).resultValue;
                TextView tv_value = createItemTextView(txt);;
                if (lis.resultList.get(j).resultValue.contains("↑") || lis.resultList.get(j).resultValue.contains("↓")) {
                    tv_value.setTextColor(Color.RED);
                } else {
                    tv_value.setTextColor(Color.BLACK);
                }
                holder.layout.addView(tv_value);
                i ++;
                j ++;
            } else {
                TextView tv_value = createItemTextView("");;
                tv_value.setTextColor(Color.BLACK);
                holder.layout.addView(tv_value);

                i ++;
            }
        }
        return convertView;
    }
    public class ViewHolder {
        public LinearLayout layout;
    }

    private TextView createItemTextView(String txt) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(1, 0, 1, 0);
        TextView tv_name = new TextView(ctx);
//		tv_name.setTextSize(18);
        tv_name.setText(txt);
        tv_name.setWidth(300);
        tv_name.setLayoutParams(lp);
        tv_name.setGravity(Gravity.CENTER);
        tv_name.setBackgroundColor(Color.WHITE);
        return tv_name;
    }
}
