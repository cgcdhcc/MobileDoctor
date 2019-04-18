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
import com.imedical.mobiledoctor.entity.AdmInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;

import java.util.List;

public class AdapterOutPat extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<AdmInfo> mListData = null ;
	private Context context;
	ViewHolder holder = null;

	public AdapterOutPat(Context context, List<AdmInfo> list) {
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
		AdmInfo p = mListData.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_frg2_outpatient, null);
			holder = new ViewHolder();
			holder.tv_patName  = (TextView) convertView.findViewById(R.id.tv_patName);
			holder.tv_inDate = (TextView) convertView.findViewById(R.id.tv_inDate);
			holder.tv_admId= (TextView) convertView.findViewById(R.id.tv_admId);
			holder.iv_head     = (ImageView)convertView.findViewById(R.id.iv_head);
			holder.tv_bedCode = (TextView)convertView.findViewById(R.id.tv_bedCode);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if("女".equals(p.patientSex)){
			holder.iv_head.setImageResource(R.drawable.all_famale);
		}else{
			holder.iv_head.setImageResource(R.drawable.all_male);
		}
		holder.tv_patName.setText(p.patientName);
		holder.tv_admId.setText(p.admId);
		holder.tv_inDate.setText(p.registerDate);
		holder.tv_bedCode.setText(p.seqCode);
		if(Const.curPat!=null&&Const.curPat.admId.equals(p.admId)){
			convertView.setBackgroundResource(R.color.mobile_gray);
			holder.tv_admId.setTextColor(Color.WHITE);
			holder.tv_inDate.setTextColor(Color.WHITE);
			holder.tv_patName.setTextColor(Color.WHITE);

		}else{
			convertView.setBackgroundResource(R.color.white);
			holder.tv_admId.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_inDate.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_patName.setTextColor(context.getResources().getColor(R.color.black));
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
		public TextView tv_patName;
		public TextView tv_bedCode;
	}
}