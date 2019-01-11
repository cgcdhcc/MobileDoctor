package com.imedical.mobiledoctor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.widget.Dic;
import com.imedical.mobiledoctor.widget.Madapter;

import java.util.List;


public class HisRecordsAdapter_2 extends Madapter {

    private Context context;
    private int selectColor = R.color.mobile_blue;
    private LayoutInflater inflater;
    private List<SeeDoctorRecord> items;
    private int selectedPosition = -1;

    public List<SeeDoctorRecord> getItems() {
        return items;
    }

    @Override
    public String getShowKey(int position , String key){
        if (key.equals("name")){
            return  items.get(position).admType+" "+items.get(position).admDate;
        }else {
            return  items.get(position).admDept+" "+items.get(position).admDate;
        }

    }





    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    public HisRecordsAdapter_2(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }



    public void setItems(List<SeeDoctorRecord> items) {
        this.items = items;
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        HisRecordsAdapter_2.ViewHolder holder;
        if(convertView == null){

            holder = new HisRecordsAdapter_2.ViewHolder();
            convertView = (View)inflater.inflate(R.layout.item_listview, parent , false);
            holder.name =(TextView) convertView.findViewById(R.id.name);
            holder.code = (TextView)convertView.findViewById(R.id.code);
            holder.employeesquery = (LinearLayout)convertView.findViewById(R.id.employeesquery);
            convertView.setTag(holder);
        }else{
            holder = (HisRecordsAdapter_2.ViewHolder)convertView.getTag();
        }

        /**
         * 该项被选中时改变背景色
         */
        if(selectedPosition == position){
            holder.employeesquery.setBackgroundColor(context.getResources().getColor(selectColor));
        }else{
            holder.employeesquery.setBackgroundColor(Color.TRANSPARENT);
        }
         String admDept=items.get(position).admDept;
         String admDate=items.get(position).admDate;
         String admType=items.get(position).admType;
         String admId=items.get(position).admId;
        holder.name.setText(admType+" "+admDate+" "+admDept);
        holder.code.setText(admId);  //也可在ITTM里去掉这一项，写在Tag里
        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView code;
        LinearLayout employeesquery;
    }

    @Override
    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }
}
