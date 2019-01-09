package com.imedical.mobiledoctor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.entity.PatientInfo;

import java.util.List;

public class AdapterPat extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<PatientInfo> mListData = null ;
	private Context context;
	ViewHolder holder = null;

	public AdapterPat(Context context, List<PatientInfo> list) {
		this.context = context;
		mListData = list ;
		this.mInflater = LayoutInflater.from(context);
	}

	public int getCount() { 
		return mListData.size();
	}

	public Object getItem(int arg0) {
		return mListData.get(arg0);
	}

	public long getItemId(int arg0) { 
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PatientInfo p = mListData.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_frg2_patient, null);
			
			holder = new ViewHolder();
			holder.tv_patName  = (TextView) convertView.findViewById(R.id.tv_patName);
			holder.tv_bedCode  = (TextView) convertView.findViewById(R.id.tv_bedCode);
			holder.tv_inDate = (TextView) convertView.findViewById(R.id.tv_inDate);
			holder.tv_admId= (TextView) convertView.findViewById(R.id.tv_admId);
			holder.tv_patMedNo = (TextView) convertView.findViewById(R.id.tv_patMedNo);
			holder.iv_head     = (ImageView)convertView.findViewById(R.id.iv_head);
			holder.tv_wardDesc = (TextView) convertView.findViewById(R.id.tv_wardDesc);
			holder.tv_1=(TextView) convertView.findViewById(R.id.tv_1);
			holder.tv_2=(TextView) convertView.findViewById(R.id.tv_2);
			holder.tv_3=(TextView) convertView.findViewById(R.id.tv_3);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if("女".equals(p.patSex)){
			holder.iv_head.setImageResource(R.drawable.all_famale);
		}else{
			holder.iv_head.setImageResource(R.drawable.all_male);
		}
		holder.tv_patName.setText(p.patName);
		holder.tv_bedCode.setText(p.bedCode+"床");
		holder.tv_patMedNo.setText(p.patMedNo);
		holder.tv_wardDesc.setText(p.wardDesc);
		holder.tv_admId.setText(p.patRegNo);
		holder.tv_inDate.setText(p.inDate);
		if(Const.curPat!=null&&Const.curPat.admId.equals(p.admId)){
//			convertView.setBackgroundResource(R.color.mobile_gray);
			convertView.setBackgroundResource(R.color.mobile_gray);
			holder.tv_admId.setTextColor(Color.WHITE);
			holder.tv_bedCode.setTextColor(Color.WHITE);
			holder.tv_inDate.setTextColor(Color.WHITE);
			holder.tv_patMedNo.setTextColor(Color.WHITE);
			holder.tv_patName.setTextColor(Color.WHITE);
			holder.tv_wardDesc.setTextColor(Color.WHITE);
			holder.tv_1.setTextColor(Color.WHITE);
			holder.tv_2.setTextColor(Color.WHITE);
			holder.tv_3.setTextColor(Color.WHITE);
		}else{
			convertView.setBackgroundResource(R.color.white);
			holder.tv_admId.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_inDate.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_patMedNo.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_wardDesc.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_patName.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_bedCode.setTextColor(context.getResources().getColor(R.color.gray));
			holder.tv_1.setTextColor(context.getResources().getColor(R.color.gray));
			holder.tv_2.setTextColor(context.getResources().getColor(R.color.gray));
			holder.tv_3.setTextColor(context.getResources().getColor(R.color.gray));

			if(p.isMainDoc.equals("1"))
			{
				holder.tv_patName.setTextColor(context.getResources().getColor(R.color.patient_color_2));
				holder.tv_bedCode.setTextColor(context.getResources().getColor(R.color.patient_color_2));
			}else
			{
				holder.tv_patName.setTextColor(context.getResources().getColor(R.color.font_base));
				holder.tv_bedCode.setTextColor(context.getResources().getColor(R.color.font_hint));
			}
		}
//		if(position == FragmentPatActivity.mIndexSelectedPat){
//			convertView.setBackgroundResource(R.color.light_gray);
//		}else{
//			convertView.setBackgroundResource(R.xml.bg_white_gray);
//		}
		return convertView;
	}

	public class ViewHolder{    
		public ImageView iv_head ;
		public TextView tv_inDate;
		public TextView tv_admId;
		public TextView tv_bedCode;
		public TextView tv_patName;
		public TextView tv_patMedNo;
		public TextView tv_wardDesc;
		public TextView tv_1,tv_2,tv_3;
	}
}