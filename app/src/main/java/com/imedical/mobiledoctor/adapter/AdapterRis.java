package com.imedical.mobiledoctor.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.round.CommonBrowserActivity;
import com.imedical.mobiledoctor.activity.round.RisActivity;
import com.imedical.mobiledoctor.activity.round.detail.Ris_DetailActivity;
import com.imedical.mobiledoctor.entity.RisReportList;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.Validator;

public class AdapterRis extends BaseAdapter {
    RisActivity ctx = null;

    public AdapterRis(RisActivity activity) {
        this.ctx = activity;
    }

    @Override
    public int getCount() {
        return ctx.mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return ctx.mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RisReportList ris = ctx.mListData.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_ris_detail, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.btn_pic_link = (ImageView) convertView.findViewById(R.id.btn_pic_link);
            holder.btn_rep_link = (ImageView) convertView.findViewById(R.id.btn_rep_link);
            holder.ll_pic_link = convertView.findViewById(R.id.ll_pic_link);
            holder.ll_rep_link = convertView.findViewById(R.id.ll_rep_link);
            holder.tv_studyId = (TextView) convertView.findViewById(R.id.tv_studyId);
            holder.ll_click = convertView.findViewById(R.id.ll_click);
            holder.tv_exam_dept=convertView.findViewById(R.id.tv_exam_dept);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            LogMe.d("(ViewHolder)convertView.getTag()_____holder:" + holder);
        }

        if (holder != null) {
            if (Validator.isBlank(ris.dicomUrl)) {
                holder.ll_pic_link.setVisibility(View.GONE);
            } else {
                holder.ll_pic_link.setVisibility(View.VISIBLE);
            }
            holder.ll_pic_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Validator.isBlank(ris.dicomUrl)) {
                        ctx.showCustom("报告未发布");
                        return;
                    } else {
                        Intent intent = new Intent(ctx, CommonBrowserActivity.class);
                        Bundle bundle = new Bundle();
                        Log.d("reportURL", ris.dicomUrl);
                        bundle.putString("reportURL", ris.dicomUrl);
                        //判断是否显示影像
                        bundle.putBoolean("isImage", true);
                        intent.putExtras(bundle);
                        ctx.startActivity(intent);
                    }
                }
            });
            holder.ll_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(ctx, Ris_DetailActivity.class);
                    i.putExtra("studyId", ris.studyId);
                    ctx.startActivity(i);
                }
            });
            if (Validator.isBlank(ris.reportUrl)) {
                holder.ll_rep_link.setVisibility(View.GONE);
            } else {
                holder.ll_rep_link.setVisibility(View.VISIBLE);
            }
            holder.ll_rep_link.setOnClickListener(new View.OnClickListener() {
                //报告链接
                @Override
                public void onClick(View v) {
                    if (Validator.isBlank(ris.reportUrl)) {
                        ctx.showCustom("报告链接为空");
                        return;
                    }
                    ///toPdfView(Uri.parse("file:///storage/sdcard0/test.pdf"));
                    if (ris.reportUrl.toLowerCase().endsWith(".pdf")) {//pdf文件
                        Log.d("reportURL", ris.reportUrl);
//                            loadPDFAndOpen(risDetail.reportURL);
                    } else {
                        Intent intent = new Intent(ctx, CommonBrowserActivity.class);
                        Bundle bundle = new Bundle();
                        Log.d("reportURL", ris.reportUrl);
                        bundle.putString("reportURL", ris.reportUrl);
                        intent.putExtras(bundle);
                        ctx.startActivity(intent);
                    }
                }

            });
            holder.tv_exam_dept.setText(ris.examDept);
            holder.tv_status.setText(ris.reportStatus);
            holder.tv_title.setText(ris.ordItemDesc);
            holder.tv_studyId.setText(ris.studyId);
            holder.tv_date.setText((ris.reportDate==null?"":ris.reportDate) + " " + (ris.reportTime==null?"":ris.reportTime));
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView tv_exam_dept;
        public TextView tv_title;
        public TextView tv_date;
        public TextView tv_studyId;
        public TextView tv_status;
        public ImageView btn_pic_link;
        public ImageView btn_rep_link;
        public LinearLayout itemLayout;
        public View ll_click, ll_rep_link, ll_pic_link;
    }
}