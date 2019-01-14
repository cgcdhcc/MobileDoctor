package com.imedical.mobiledoctor.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.imedical.mobiledoctor.entity.LabelValue;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.LogMe;
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
			ImageView iv = (ImageView) convertView.findViewById(R.id.groupIcon);
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
				holder.tv_orderDoctor = (TextView) convertView
						.findViewById(R.id.tv_orderDoctor);
				holder.tv_stopOrderDoctor = (TextView) convertView
						.findViewById(R.id.tv_stopOrderDoctor);
				holder.tv_stopOrderDate = (TextView) convertView
						.findViewById(R.id.tv_stopOrderDate);
				holder.tv_stopOrderTime = (TextView) convertView
						.findViewById(R.id.tv_stopOrderTime);

				holder.tv_orderStatus = (TextView) convertView
						.findViewById(R.id.tv_orderStatus);
				holder.tv_ordStartDate = (TextView) convertView
						.findViewById(R.id.tv_ordStartDate);
				holder.tv_ordStartTime = (TextView) convertView
						.findViewById(R.id.tv_ordStartTime);
				holder.tv_priority = (TextView) convertView
						.findViewById(R.id.tv_priority);
				holder.tv_doseQty = (TextView) convertView
						.findViewById(R.id.tv_doseQty);
				holder.tv_frequency = (TextView) convertView
						.findViewById(R.id.tv_frequency);
				holder.tv_instruction = (TextView) convertView
						.findViewById(R.id.tv_instruction);
				holder.tv_qty = (TextView) convertView
						.findViewById(R.id.tv_qty);

				holder.tv_info = (TextView) convertView
						.findViewById(R.id.tv_info);
				holder.ll_detail = convertView.findViewById(R.id.ll_detail);
				holder.tl_content = convertView.findViewById(R.id.tl_content);
				holder.btn_stop_or_cancel = (Button)convertView
						.findViewById(R.id.btn_stop_or_cancel);
				holder.iv_icon = (ImageView) convertView
						.findViewById(R.id.iv_icon);
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
			holder.ll_detail.setVisibility(View.GONE);
			final View ll_detail = holder.ll_detail;
//			if ("drug.png".equals(b.icoFile)) {
//				holder.iv_icon.setImageResource(R.drawable.lis_drug);
//			} else {
//				holder.iv_icon.setImageResource(R.drawable.lis_other);
//			}
//			OnClickListener cls = new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if(v.getId() == R.id.tl_content || v.getId() == R.id.tv_info){
//						Intent i =new Intent(context,FormOrderItemActivity.class);
//						i.putExtra("Item", b);
//						i.putExtra("children",(Serializable)children);
//						i.putExtra("childPosition", childPosition);
//						context.startActivity(i);
////						ll_detail.setVisibility((ll_detail.getVisibility()==View.VISIBLE)?(View.GONE):(View.VISIBLE));
//					}
//				}
//			};

			String text = null;
			//TODO 修改图片
			final String info = text;

			holder.tv_itemDesc.setText("("+b.seqNo +")" +b.itemDesc);
			holder.tv_orderDoctor.setText(b.orderDoctor);
			holder.tv_stopOrderDoctor.setText(b.stopOrderDoctor);
			holder.tv_stopOrderDate.setText(b.stopOrderDate);
			holder.tv_orderStatus.setText(b.orderStatus);
			holder.tv_ordStartDate.setText(b.ordStartDate);
			holder.tv_ordStartTime.setText(b.ordStartTime);
			holder.tv_priority.setText(b.priority);
			holder.tv_doseQty.setText(b.doseQty+b.doseUom);
			holder.tv_frequency.setText(b.frequency);
			holder.tv_instruction.setText(b.instruction);
			holder.tv_qty.setText(b.qty+b.uom);
			holder.tv_info.setText(getInfo(b));
			holder.tv_stopOrderTime.setText(b.stopOrderTime);
		} catch (Exception e) {
			Log.e("EEEEEEEEEEEEEE", "********childPosition" + childPosition);
			e.printStackTrace();
		}
		return convertView;

	}

	private String getInfo(final OrderItem b) {
		StringBuilder sb = new StringBuilder();
		if (!Validator.isBlank(b.doseQty)) {
			sb.append(b.doseQty + b.doseUom + ",");
		}
		if (!Validator.isBlank(b.frequency)) {
			sb.append(b.frequency).append(",");
		}
		if (!Validator.isBlank(b.instruction)) {
			sb.append(b.instruction).append(",");
		}
		sb.append(b.ordStartDate);
		if (!Validator.isBlank(b.stopOrderDate)) {
			sb.append("~").append(b.stopOrderDate);
		}
		sb.append(",");
		if (!Validator.isBlank(b.orderStatus)) {
			sb.append(b.orderStatus);
		}
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
			final PatientInfo p = context.mPatientCurrSelected;
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
		public ImageView iv_icon;
		public Button btn_stop_or_cancel;
		public View ll_detail;
		public View tl_content;
		public TextView tv_itemDesc;
		public TextView tv_orderDoctor;
		public TextView tv_stopOrderDoctor;
		public TextView tv_stopOrderDate;
		public TextView tv_stopOrderTime;
		public TextView tv_orderStatus;
		public TextView tv_ordStartDate;
		public TextView tv_ordStartTime;
		public TextView tv_priority;
		public TextView tv_doseQty;
		public TextView tv_frequency;
		public TextView tv_instruction;
		public TextView tv_qty;

		public TextView tv_info;
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