package com.imedical.mobiledoctor.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.OrderItemManager;
import com.imedical.mobiledoctor.activity.round.OrdersActivity;
import com.imedical.mobiledoctor.activity.round.detail.OrderDetailActivity;
import com.imedical.mobiledoctor.entity.LabelValue;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.DialogUtil;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.util.Validator;

import java.io.Serializable;
import java.util.List;

public class OrderItemAdapter extends BaseExpandableListAdapter {
	private String mInfo = null;
	private OrdersActivity context;

	private List<LabelValue> groupArray;
	private List<List<OrderItem>> childDataList;
	private LayoutInflater mInflater;
	
	public OrderItemAdapter(OrdersActivity a, List<LabelValue> parentList,
							List childArray) {
		this.context = a;
		this.groupArray = parentList;
		this.childDataList = childArray;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		try {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.children_adapter_orders, null);
			}
			TextView groupto = (TextView) convertView.findViewById(R.id.groupto);
			LabelValue d = groupArray.get(groupPosition);
			groupto.setText(d.label);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		try {
			
			final List<OrderItem> children = childDataList.get(groupPosition);
			final OrderItem b = children.get(childPosition);
			ViewHolder holder = null;
			if (convertView == null) {
				 holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.adapter_orders_item,
						null);
				holder.tv_itemDesc = (TextView) convertView
						.findViewById(R.id.tv_itemDesc);
				holder.tv_doseinfo = convertView.findViewById(R.id.tv_doseinfo);
				holder.tv_orderinfo = convertView.findViewById(R.id.tv_orderinfo);
				holder.btn_stop_or_cancel = convertView.findViewById(R.id.btn_stop_or_cancel);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if ("0".equals(b.stopPerm)) {
				holder.btn_stop_or_cancel.setVisibility(View.GONE);
			} else {
				//FIXME
				holder.btn_stop_or_cancel.setVisibility(View.VISIBLE);
			}
			holder.tv_itemDesc.setText("("+b.seqNo +") " +b.itemDesc);
			holder.tv_doseinfo.setText(getDoseInfo(b));
			holder.tv_orderinfo.setText(getOrderInfo(b));
			holder.btn_stop_or_cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					DialogUtil.showAlertYesOrNo(context, "操作确认", "确定停止该遗嘱吗？", new MyCallback<Boolean>() {
						@Override
						public void onCallback(Boolean value) {
							if(value){
								stopOrderItem(b, children, childPosition);
							}
						}
					});
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent=new Intent(context, OrderDetailActivity.class);
					intent.putExtra("orderItem", children.get(childPosition));
					context.startActivity(intent);
				}
			});
		} catch (Exception e) {
			Log.e("EEEEEEEEEEEEEE", "********childPosition" + childPosition);
			e.printStackTrace();
		}
		return convertView;

	}

	private String getOrderInfo(final OrderItem b){
		StringBuilder sb = new StringBuilder();
		sb.append(Validator.isBlank(b.orderDate)?"":b.orderDate+" ");
		sb.append(Validator.isBlank(b.orderStatus)?"":b.orderStatus+" ");
		return sb.toString();
	}
	private String getDoseInfo(final OrderItem b) {
		StringBuilder sb = new StringBuilder();
		sb.append(Validator.isBlank(b.doseQty)?"":b.doseQty+" ");
		sb.append(Validator.isBlank(b.doseUom)?"":b.doseUom+" ");
		sb.append(Validator.isBlank(b.frequency)?"":b.frequency+" ");
		sb.append(Validator.isBlank(b.instruction)?"":b.instruction+" ");
		sb.append(Validator.isBlank(b.ordStartDate)?"":b.ordStartDate+" ");
		sb.append(Validator.isBlank(b.stopOrderDate)?"":"~"+b.stopOrderDate+" ");
		return sb.toString();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		int size = 0;
		try {
			LogMe.d(">>>>>>childDataList.size()", ">>>>>>childDataList.size()"
					+ childDataList.size() + "---groupPosition:"
					+ groupPosition);
			size = childDataList.get(groupPosition).size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	
	
	public void stopOrderItem(final OrderItem b,
			final List<OrderItem> childList, final int childPos) {
		try {
			final LoginInfo u = Const.loginInfo;
			final PatientInfo p = Const.curPat;
			new Thread() {
				public void run() {
					try {
						OrderItemManager.stopOrderItem(u.userCode, p.admId, b.ordItemId);
						childList.remove(childPos);
						context.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								notifyDataSetChanged();
//								DialogUtil.showToasMsg(context, "执行成功！");
							}
						});
					} catch (Exception e) {
//						DialogUtil.showToasMsg(context, e.getMessage());
						e.printStackTrace();
					}
				};
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public class ViewHolder {
		public TextView tv_itemDesc;
		public TextView tv_doseinfo;
		public TextView tv_orderinfo;
		public Button btn_stop_or_cancel;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childDataList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return getGroup(groupPosition);
	}

	@Override
	public int getGroupCount() {

		return groupArray.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}