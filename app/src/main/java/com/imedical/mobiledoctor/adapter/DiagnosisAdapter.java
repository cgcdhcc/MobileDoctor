package com.imedical.mobiledoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.entity.Diagnosis;
import java.util.List;

public class DiagnosisAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Diagnosis> mListData = null ;
	private Context context;
	ViewHolder holder = null;

	public DiagnosisAdapter(Context context, List<Diagnosis> list) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		mListData = list ;
	}

	public int getCount() { 
		return mListData.size();
	}

	public Object getItem(int arg0) { 
		return mListData.get(arg0);
	}

	public long getItemId(int arg0) { 
		return arg0;
	}
	private int  selectItem=-1;   

	public  void setSelectItem(int selectItem) {   
        this.selectItem = selectItem;   
   }   

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
 	
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_diagnosis, null);
			holder.tv_diaDesc = (TextView) convertView.findViewById(R.id.tv_diaDesc);
			holder.tv_diaDate = (TextView) convertView.findViewById(R.id.tv_diaDate);
			holder.tv_diaDoc = (TextView) convertView.findViewById(R.id.tv_diaDoc);
			holder.tv_diaType = (TextView) convertView.findViewById(R.id.tv_diaType);
			holder.tv_diaTime = (TextView) convertView.findViewById(R.id.tv_diaTime);
			holder.tv_diaStat = (TextView) convertView.findViewById(R.id.tv_diaStat);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		 Diagnosis b = mListData.get(position);
		 holder.tv_diaDesc.setText(b.diaDesc);
		 holder.tv_diaDate.setText(b.diaDate);
		 holder.tv_diaDoc.setText(b.diaDoctor);

		 holder.tv_diaType.setText(b.diaType);
		 holder.tv_diaTime.setText(b.diaTime);
	     holder.tv_diaStat.setText("备注:"+b.diaNote==null?"无":b.diaNote);
		 
		if (position == selectItem) {    
			//Log.d("GetProduByNo", "------------------>......" );	 
        }else{
        	//convertView.setBackgroundColor(0);   
        } 
		
		return convertView;
	}

	public void distoryBitmap() {
//		if (null != bm)
//			bm.recycle();
//		bm = null;
	}

	/**
	 *
	 */
	public class ViewHolder {    
		public TextView tv_diaStat;
		public TextView tv_diaDesc;
	    public TextView tv_diaDate;
	    public TextView tv_diaDoc;
	    public TextView tv_diaType;
	    public TextView tv_diaTime;
	}
	
}