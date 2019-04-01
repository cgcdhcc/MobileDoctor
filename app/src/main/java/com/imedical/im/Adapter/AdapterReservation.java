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
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_desc.setText(SO.orderName);
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
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListUnChecked();
                holder.checkBox.performClick();
                if(ListIsChecked()){
                    ctx.showPop();
                }
            }
        });
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
        public TextView tv_desc;
        public CheckBox checkBox;
    }
}
