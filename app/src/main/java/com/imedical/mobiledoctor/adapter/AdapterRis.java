package com.imedical.mobiledoctor.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.round.CommonBrowserActivity;
import com.imedical.mobiledoctor.activity.round.RisActivity;
import com.imedical.mobiledoctor.activity.round.detail.Ris_DetailActivity;
import com.imedical.mobiledoctor.entity.RisReport;
import com.imedical.mobiledoctor.entity.RisReportList;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.Validator;

public class AdapterRis extends BaseAdapter {
    RisActivity ctx=null;

    public AdapterRis(RisActivity activity) {
        this.ctx=activity;
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
            holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            holder.tv_studyId = (TextView)convertView.findViewById(R.id.tv_studyid);
            holder.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
            holder.tv_regNo = (TextView)convertView.findViewById(R.id.tv_regno);
            holder.tv_admDept = (TextView)convertView.findViewById(R.id.tv_admDept);
            holder.tv_ward = (TextView)convertView.findViewById(R.id.tv_ward_desc);
            holder.tv_dept = (TextView)convertView.findViewById(R.id.tv_exam_dept);
            holder.tv_bed = (TextView)convertView.findViewById(R.id.tv_bed);
            holder.tv_status = (TextView)convertView.findViewById(R.id.tv_status);
            holder.tv_diagnosis = (TextView)convertView.findViewById(R.id.tv_diagnosis);
            holder.tv_res_desc = (TextView)convertView.findViewById(R.id.tv_res_desc);
            //holder.tv_res_desc.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            holder.tv_exam_desc = (TextView)convertView.findViewById(R.id.tv_exam_desc);
            //holder.tv_exam_desc.setEllipsize(TextUtils.TruncateAt.MIDDLE);//防止末位半个字体显示不全
            holder.btn_pic_link = (ImageView) convertView.findViewById(R.id.btn_pic_link);
            holder.btn_rep_link = (ImageView)convertView.findViewById(R.id.btn_rep_link);
            holder.ll_pic_link=convertView.findViewById(R.id.ll_pic_link);
            holder.ll_rep_link=convertView.findViewById(R.id.ll_rep_link);
            holder.tv_risRegId=(TextView)convertView.findViewById(R.id.tv_risRegId);
            holder.ll_click = convertView.findViewById(R.id.ll_click);
            holder.itemLayout = (LinearLayout)convertView.findViewById(R.id.item_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
            LogMe.d("(ViewHolder)convertView.getTag()_____holder:"+holder);
        }
        if (ctx.mListData2.size() > 0) {
            final RisReport risDetail = ctx.mListData2.get(position).get(0);
            if (holder != null) {
                if(Validator.isBlank(risDetail.imageURL)){
                    holder.ll_pic_link.setVisibility(View.GONE);
                }else {
                    holder.ll_pic_link.setVisibility(View.VISIBLE);
                }
                holder.ll_pic_link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Validator.isBlank(risDetail.imageURL)){
                            ctx.showCustom("报告未发布");
                            return ;
                        }else {
                            Intent intent = new Intent(ctx, CommonBrowserActivity.class);
                            Bundle bundle = new Bundle();
                            Log.d("reportURL", risDetail.imageURL);
                            bundle.putString("reportURL", risDetail.imageURL);
                            //判断是否显示影像
                            bundle.putBoolean("isImage", true);
                            intent.putExtras(bundle);
                            ctx.startActivity(intent);
                        }
                    }
                });
                holder.ll_click.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View arg0) {
                        Intent i = new Intent(ctx, Ris_DetailActivity.class);
                        i.putExtra("content_1", risDetail.examDescEx);
                        i.putExtra("content_2", risDetail.resultDescEx);
                        ctx.startActivity(i);
                    }});
                if(Validator.isBlank(risDetail.reportURL)){
                    holder.ll_rep_link.setVisibility(View.GONE);
                }else{
                    holder.ll_rep_link.setVisibility(View.VISIBLE);
                }
                holder.ll_rep_link.setOnClickListener(new View.OnClickListener() {
                    //报告链接
                    @Override
                    public void onClick(View v) {
                        if(Validator.isBlank(risDetail.reportURL)){
                            ctx.showCustom("报告链接为空");
                            return ;
                        }
//							DialogUtil.showToasMsg(mActivity,risDetail.reportURL.toString());
                        //lqz--add 外网不能看图片链接，提示一下
//							if(!SettingManager.isIntranet(mActivity)){
//								DialogUtil.showToasMsg(mActivity,"外网不允许查看图片链接");
//								return ;
//							}
                        ///risDetail.reportURL = "http://222.132.155.198:81/mhc/ipad/icu/1.pdf";
                        ///toPdfView(Uri.parse("file:///storage/sdcard0/test.pdf"));
                        if(risDetail.reportURL.toLowerCase().endsWith(".pdf")){//pdf文件
                            Log.d("reportURL", risDetail.reportURL);
//                            loadPDFAndOpen(risDetail.reportURL);
                        }else{
                            Intent intent = new Intent(ctx, CommonBrowserActivity.class);
                            Bundle bundle = new Bundle();
                            Log.d("reportURL", risDetail.reportURL);
                            bundle.putString("reportURL", risDetail.reportURL);
                            intent.putExtras(bundle);
                            ctx.startActivity(intent);
                        }
                    }

                });
                holder.tv_status.setText(ris.reportStatus);
                holder.tv_title.setText(ris.ordItemDesc);
                holder.tv_title.setText(ris.ordItemDesc);
                holder.tv_risRegId.setText(ris.risRegId);
                holder.tv_studyId.setText(ris.studyId);
                holder.tv_date.setText(ris.reportDate + " " + ris.reportTime);

                if (risDetail != null) {
                    holder.tv_regNo.setText(risDetail.regNo);
                    holder.tv_ward.setText(risDetail.wardDesc);
                    holder.tv_dept.setText(risDetail.examDept);
                    holder.tv_admDept.setText(risDetail.admDept);
                    holder.tv_bed.setText(risDetail.bedCode);
                    holder.tv_diagnosis.setText(risDetail.diagnosis);
                    holder.tv_exam_desc.setText( risDetail.examDescEx);
                    holder.tv_res_desc.setText(risDetail.resultDescEx);
                }
            }
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView tv_title;
        public TextView tv_studyId;
        public TextView tv_date;
        public TextView tv_regNo;
        public TextView tv_ward;
        public TextView tv_dept;
        public TextView tv_bed;
        public TextView tv_admDept;
        public TextView tv_risRegId;
        public TextView tv_status;
        public TextView tv_diagnosis;
        public TextView tv_exam_desc;
        public TextView tv_res_desc;
        public ImageView btn_pic_link;
        public ImageView btn_rep_link;
        public LinearLayout itemLayout;
        public View ll_click,ll_rep_link,ll_pic_link;
    }
}