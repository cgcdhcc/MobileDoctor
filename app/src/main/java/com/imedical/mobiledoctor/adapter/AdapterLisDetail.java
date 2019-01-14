package com.imedical.mobiledoctor.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.round.detail.Lis_DetailActivity;
import com.imedical.mobiledoctor.entity.LisReportItem;

public class AdapterLisDetail extends BaseAdapter {
    Lis_DetailActivity ctx=null;
    public AdapterLisDetail(Lis_DetailActivity activity) {
        this.ctx=activity;
    }

    @Override
    public int getCount() {
        return ctx.mDataListLis.size();
    }

    @Override
    public Object getItem(int position) {
        return ctx.mDataListLis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        LisReportItem lisReportItem = ctx.mDataListLis.get(position);
        if (("↑").equals(lisReportItem.flagUpDown)
                || ("↓").equals(lisReportItem.flagUpDown)||("h").equals(lisReportItem.flagUpDown)
                || ("l").equals(lisReportItem.flagUpDown)) {
            return 0;// RED
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        LisReportItem lisReportItem = ctx.mDataListLis.get(position);
        ViewHolder_lisDetail holder_listDetail;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder_listDetail = new ViewHolder_lisDetail();
            convertView = LayoutInflater.from(ctx).inflate(
                    R.layout.adapter_lis_detail, null);
            holder_listDetail.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder_listDetail.tv_abb = (TextView) convertView
                    .findViewById(R.id.tv_abb);
            holder_listDetail.tv_result = (TextView) convertView
                    .findViewById(R.id.tv_result);
            holder_listDetail.tv_unit = (TextView) convertView
                    .findViewById(R.id.tv_unit);
            holder_listDetail.tv_error = (TextView) convertView
                    .findViewById(R.id.tv_error);
            holder_listDetail.tv_range = (TextView) convertView
                    .findViewById(R.id.tv_range);
            switch (type) {
                case 0:
                    holder_listDetail.tv_name.setTextColor(Color.RED);
                    holder_listDetail.tv_abb.setTextColor(Color.RED);
                    holder_listDetail.tv_result.setTextColor(Color.RED);
                    holder_listDetail.tv_error.setTextColor(Color.RED);
                    holder_listDetail.tv_unit.setTextColor(Color.RED);
                    holder_listDetail.tv_range.setTextColor(Color.RED);
                    break;
                case 1:
                    break;
            }
            convertView.setTag(holder_listDetail);
        } else {
            holder_listDetail = (ViewHolder_lisDetail) convertView.getTag();
        }
        holder_listDetail.tv_name.setText(lisReportItem.itemDesc);
        holder_listDetail.tv_abb.setText(lisReportItem.abbreviation);
        holder_listDetail.tv_result.setText(lisReportItem.resultValue);
        holder_listDetail.tv_unit.setText(lisReportItem.unit);
        holder_listDetail.tv_error.setText(lisReportItem.flagUpDown);
        holder_listDetail.tv_range.setText(lisReportItem.naturalRange);
        return convertView;
    }

    public class ViewHolder_lisDetail {
        public TextView tv_name;
        public TextView tv_abb;
        public TextView tv_result;
        public TextView tv_unit;
        public TextView tv_error;
        public TextView tv_range;
    }
}
