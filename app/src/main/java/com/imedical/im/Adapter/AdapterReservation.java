package com.imedical.im.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.imedical.im.activity.AddDiagnosisActivity;
import com.imedical.im.activity.ReservationListActivity;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.round.detail.Lis_DetailActivity;
import com.imedical.mobiledoctor.entity.LisReportItem;
import com.imedical.mobiledoctor.entity.ServiceOrder;
import com.imedical.mobiledoctor.util.Validator;

public class AdapterReservation extends BaseAdapter {
    ReservationListActivity ctx=null;
    public AdapterReservation(ReservationListActivity activity) {
        this.ctx=activity;
    }

    @Override
    public int getCount() {
        return ctx.list_data.size();
    }

    @Override
    public Object getItem(int position) {
        return ctx.list_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
      final   ServiceOrder SO = ctx.list_data.get(position);
      final   ViewHolder holder;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_reservation, null);
            holder.ll_checkBox = convertView.findViewById(R.id.ll_checkBox);
            holder.ll_conent =convertView.findViewById(R.id.ll_conent);
            holder.tv_desc_yes = (TextView) convertView.findViewById(R.id.tv_desc_yes);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_appStatus = (TextView) convertView.findViewById(R.id.tv_appStatus);
            holder.tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_desc.setText(SO.orderName);
        holder.tv_desc_yes.setText(SO.orderName);
        holder.tv_appStatus.setText(SO.appStatus);
        holder.tv_date.setText("预约时间:"+SO.bookedDate+" "+SO.bookedTime);
        holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.showCustom("点击了");
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int IsGroup=SO.IsGroup;
                for(ServiceOrder so:ctx.list_data){
                    if(IsGroup==so.IsGroup){
                        so.IsChecked=isChecked;
                    }
                }
                notifyDataSetChanged();
            }
        });
        if(SO.IsChecked){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }
       holder.ll_checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ListUnChecked();
               holder.checkBox.performClick();
               if(ListIsChecked()){
                   ctx.showPop();
               }
           }
       });
        String St=SO.appStatus==null?"": SO.appStatus;
        if(St.equals("预约")){
            holder.ll_checkBox.setVisibility(View.GONE);
            holder.ll_conent.setVisibility(View.VISIBLE);
        }else{
            holder.ll_conent.setVisibility(View.GONE);
            holder.ll_checkBox.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private void ListUnChecked(){
        for(ServiceOrder so:ctx.list_data){
            so.IsChecked=false;
        }
    }

    private boolean ListIsChecked(){
        boolean rtn=false;
        for(ServiceOrder so:ctx.list_data){
            if( so.IsChecked){
                rtn=true;
                break;
            }
        }
        return  rtn;
    }



    public class ViewHolder {
        public TextView tv_desc,tv_desc_yes,tv_date,tv_cancel,tv_appStatus;
        public CheckBox checkBox;
        public View ll_checkBox;
        public View ll_conent;
    }
}
