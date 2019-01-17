package com.imedical.mobiledoctor.activity.frg_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.activity.frg_1.detail.FormConsultInfoActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.ConsultData;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.DialogUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.widget.ListViewPull;

import java.util.ArrayList;
import java.util.List;

public class ConsultActivity extends BaseActivity implements OnClickListener{
	public TextView tv_isgoing, tv_isgoing_line, tv_hasfinish, tv_hasfinish_line;
	private ListViewPull mListView;
	public ArrayAdapter<String> adapter;
	private List<ConsultData> mListData = new ArrayList<ConsultData>() ;
	private String mConLoad="" ;
	private AdapterPat mAdapterPat;
	private TextView tv_startDate;
	private TextView tv_endDate;
	private View ll_nodata;
	private int conStatus=0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frg1_consult);
		setTitle("会诊管理");
		if(Const.loginInfo !=null){
			initUI();
			doCheck();
		}
	}




	private void resetData() {
		mConLoad = "" ;
		mListView.resetStatu();
		mListData.clear();
		mListView.startLoading();
		mAdapterPat.notifyDataSetChanged();
		ll_nodata.setVisibility(View.VISIBLE);
		mListView.setVisibility(View.GONE);
	}

	public void initUI() {
		ll_nodata= findViewById(R.id.ll_nodata);
		tv_isgoing = (TextView) findViewById(R.id.tv_isgoing);
		tv_isgoing.setOnClickListener(this);

		tv_isgoing_line = (TextView) findViewById(R.id.tv_isgoing_line);

		tv_hasfinish = (TextView) findViewById(R.id.tv_hasfinish);
		tv_hasfinish.setOnClickListener(this);


		tv_hasfinish_line = (TextView) findViewById(R.id.tv_hasfinish_line);
		tv_endDate   = (TextView)findViewById(R.id.tv_endDate);
		tv_startDate = (TextView)findViewById(R.id.tv_startDate);
		tv_startDate.setText(DateUtil.getDateTodayBefore("yyyy-MM-dd",-7));
		tv_endDate.setText(DateUtil.getDateToday("yyyy-MM-dd"));
		tv_startDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DateTimePickDialogUtil(ConsultActivity.this).datePicKDialoBefor(tv_startDate.getText().toString(),DateUtil.getDateToday(null),new MyCallback(){
					@Override
					public void onCallback(Object date) {
						tv_startDate.setText(date.toString());
						doQuery();
					}
				}).show();
			}
		});
		tv_endDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DateTimePickDialogUtil(ConsultActivity.this).datePicKDialoBefor(tv_endDate.getText().toString(),DateUtil.getDateToday(null),new MyCallback(){
					@Override
					public void onCallback(Object date) {
						tv_endDate.setText(date.toString());
						doQuery();
					}
				}).show();
			}
		});
		mListView     = (ListViewPull)findViewById(R.id.lv_data);
		mAdapterPat = new AdapterPat();
		mListView.setAdapter(mAdapterPat);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,long arg3) {
				Intent i = new Intent(ConsultActivity.this, FormConsultInfoActivity.class );
				i.putExtra("ConsultData", mListData.get(pos));
				startActivity(i);
			}

		});
		mListView.setXListViewListener(new ListViewPull.IXListViewListener(){
			@Override
			public void onRefresh() {
			}

			@Override
			public void onClickLoadMore() {
				loadData();
			}

		});
		mListView.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
					case OnScrollListener.SCROLL_STATE_IDLE:
						if (view.getLastVisiblePosition() == (view.getCount() - 1)//滚动到底部
								&& mConLoad!=null ) {//还有新的回复
							loadData();
						}
						break;
					case OnScrollListener.SCROLL_STATE_FLING:
						// Log.v("开始滚动：SCROLL_STATE_FLING");
						break;
				}
			}
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
			}

		});



	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_isgoing:
				if (conStatus != 0) {
					conStatus = 0;
					doCheck();
				}
				break;
			case R.id.tv_hasfinish:
				if (conStatus != 1) {
					conStatus = 1;
					doCheck();
				}
				break;
		}
	}
	public void doCheck() {
		if (conStatus == 0) {
			tv_isgoing.setTextColor(getResources().getColor(R.color.text_base));
			tv_isgoing.getPaint().setFakeBoldText(true);
			tv_isgoing_line.setVisibility(View.VISIBLE);

			tv_hasfinish.setTextColor(getResources().getColor(R.color.text_grayblack));
			tv_hasfinish.getPaint().setFakeBoldText(false);
			tv_hasfinish_line.setVisibility(View.INVISIBLE);
		} else {
			tv_hasfinish.setTextColor(getResources().getColor(R.color.text_base));
			tv_hasfinish.getPaint().setFakeBoldText(true);
			tv_hasfinish_line.setVisibility(View.VISIBLE);

			tv_isgoing.setTextColor(getResources().getColor(R.color.text_grayblack));
			tv_isgoing.getPaint().setFakeBoldText(false);
			tv_isgoing_line.setVisibility(View.INVISIBLE);
		}
		doQuery();
	}

	//按时间分组、按类型分组监听事件
	private void loadData() {
		if( mConLoad==null || !mListView.isThereMore() ){
			mListView.endLoad(false);
			return ;
		}
		final String startDate = tv_startDate.getText().toString();
		final String endDate   = tv_endDate.getText().toString();
		showProgress();
		new Thread(){
			String mInfo="查询失败，请稍后再试";
			List<ConsultData> tempList ;
			public void run() {
				try {
					tempList = BusyManager.listConsultData(Const.loginInfo.userCode, Const.loginInfo.defaultDeptId, startDate, endDate, conStatus+"");
				} catch (Exception e) {
					mInfo = e.getMessage();
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgress();
						if(tempList!=null){
							if(tempList.size()>0){
								ll_nodata.setVisibility(View.GONE);
								mListView.setVisibility(View.VISIBLE);
								mConLoad = tempList.get(tempList.size()-1).conLoad ;
								mListData.addAll(tempList);
								mAdapterPat.notifyDataSetChanged();
								if(mConLoad==null){
									mListView.endLoad(false);
								}else{
									mListView.endLoad(true);
								}
							}else{
								ll_nodata.setVisibility(View.VISIBLE);
								mListView.setVisibility(View.GONE);
								mListView.endLoad(false);
							}

						}else{
							showToast( mInfo);
						}
					}
				});
			};
		}.start();

	}

	public class AdapterPat extends BaseAdapter {
		ViewHolder holder = null;

		public AdapterPat() {
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

		public View getView(int position, View convertView, ViewGroup parent) {
			ConsultData c = mListData.get(position);
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_consult_data, null);

				holder = new ViewHolder();
				holder.tv_patBed  = (TextView) convertView.findViewById(R.id.tv_patBed);
				holder.tv_mainDiag = (TextView) convertView.findViewById(R.id.tv_mainDiag);
				holder.tv_patName  = (TextView) convertView.findViewById(R.id.tv_patName);
				holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
				holder.tv_reportMemo = (TextView) convertView.findViewById(R.id.tv_reportMemo);
				holder.tv_appDoc = (TextView) convertView.findViewById(R.id.tv_appDoc);
				holder.tv_appDateTime = (TextView) convertView.findViewById(R.id.tv_appDateTime);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_patBed.setText( c.patBed+"床");
			holder.tv_mainDiag.setText( c.mainDiag);
			holder.tv_patName.setText(c.patName);
			holder.tv_type.setText("会诊类型："+c.type);
			holder.tv_appDoc.setText("申请人："+c.appDoc+" | "+c.appLoc);
			holder.tv_appDateTime.setText("申请时间："+c.appDateTime);

			return convertView;
		}

		/**
		 *
		 */
		public class ViewHolder{
			public TextView tv_appDateTime;
			public TextView tv_appDoc;
			public TextView tv_type;
			public TextView tv_mainDiag;
			public TextView tv_patBed;
			public TextView tv_reportMemo;
			public TextView tv_patName;
		}



	}
	private void doQuery() {
		resetData();
		loadData();
	}




	@Override
	protected void onRightBtnClicked() {
		// TODO Auto-generated method stub

	}
}
