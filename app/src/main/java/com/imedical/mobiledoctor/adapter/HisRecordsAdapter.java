package com.imedical.mobiledoctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.SysManager;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

import java.util.ArrayList;
import java.util.List;

public class HisRecordsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<SeeDoctorRecord> mListData = new ArrayList<SeeDoctorRecord>();
    private BaseRoundActivity context;
    ViewHolder holder = null;

    public HisRecordsAdapter(BaseRoundActivity context, List<SeeDoctorRecord> list) {
        this.context = context;
        if (mListData != null) {
            this.mListData.addAll(list);
        }
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mListData.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    private int selectItem = -1;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.records_adapter, null);
            holder.tv_admDate = (TextView) convertView.findViewById(R.id.tv_admDate);
            holder.tv_admDept = (TextView) convertView.findViewById(R.id.tv_admDept);
            holder.tv_admType = (TextView) convertView.findViewById(R.id.tv_admType);
            holder.tv_success = (ImageView) convertView.findViewById(R.id.tv_success);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SeeDoctorRecord b = mListData.get(position);
        //列表第一项为本次就诊记录,显示"默认"
        if (position == 0) {
            holder.tv_admType.setText("[默认]"+SysManager.getAdmTypeDesc(b.admType));
            holder.tv_admDate.setText(b.admDate);
            holder.tv_admDept.setText(b.admDept);
        } else {
            holder.tv_admDate.setText(b.admDate);
            holder.tv_admDept.setText(b.admDept);
            holder.tv_admType.setText(SysManager.getAdmTypeDesc(b.admType));
        }
        if (position == context.selectPos) {
            holder.tv_admDate.setTextColor(context.getResources().getColor(R.color.mobile_blue));
            holder.tv_admDept.setTextColor(context.getResources().getColor(R.color.mobile_blue));
            holder.tv_admType.setTextColor(context.getResources().getColor(R.color.mobile_blue));
//            holder.tv_success.setVisibility(View.VISIBLE);
        } else {
            holder.tv_admDate.setTextColor(context.getResources().getColor(R.color.gray));
            holder.tv_admDept.setTextColor(context.getResources().getColor(R.color.gray));
            holder.tv_admType.setTextColor(context.getResources().getColor(R.color.gray));
//            holder.tv_success.setVisibility(View.GONE);
        }

        return convertView;
    }

    /**
     *
     */
    public class ViewHolder {
        public TextView tv_admDept;
        public TextView tv_admDate;
        public TextView tv_admType;
        public ImageView tv_success;
    }

}

