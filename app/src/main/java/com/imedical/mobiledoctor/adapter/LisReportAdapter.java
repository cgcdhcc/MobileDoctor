package com.imedical.mobiledoctor.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.round.LisActivity;
import com.imedical.mobiledoctor.activity.round.detail.Lis_DetailActivity;
import com.imedical.mobiledoctor.entity.LisReportList;

import java.util.List;
import java.util.Map;

public class LisReportAdapter extends SimpleExpandableListAdapter {
    LisActivity ctx=null;
    public LisReportAdapter(LisActivity context,
                            List<Map<String, String>> groupData, int expandedGroupLayout,
                            String[] groupFrom, int[] groupTo,
                            List<List<Map<String, LisReportList>>> childData,
                            int childLayout, String[] childFrom, int[] childTo) {
        super(context, groupData, expandedGroupLayout, groupFrom, groupTo,
                childData, childLayout, childFrom, childTo);
        ctx=context;
    }

    @Override
    public View getChildView(final int groupPosition,
                             final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        int type = getChildType(groupPosition, childPosition);
        List<Map<String, LisReportList>> childList = ctx.childDataList
                .get(groupPosition);
        Map<String, LisReportList> map = childList.get(childPosition);
        final LisReportList l = map.get("child");
        final String labNo = l.ordLabNo;
        final String arcItemId = l.arcItemId;
        ViewHolderLis holder = null;
        if (convertView == null) {
            holder = new ViewHolderLis();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_lis_child, null);
            holder.itemLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_layout);
            holder.tv_state = (TextView) convertView.findViewById(R.id.lis_child_state);
            holder.tv_time = (TextView) convertView.findViewById(R.id.lis_child_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderLis) convertView.getTag();
        }
        switch (type) {
            case 1:
                //存在异常
                holder.tv_state.setTextColor(Color.RED);
                break;
            case 2:
                //报告未出
                holder.tv_state.setTextColor(Color.GRAY);
                break;
            case 0:
                //正常的为黑色
                break;
        }
        if (groupPosition ==ctx. mIndexSelectedGroup && childPosition == ctx.mIndexSelectedPat) {
//            convertView.setBackgroundResource(R.drawable.bg_lis_2hov);
        } else {
//            convertView.setBackgroundResource(R.drawable.lis_left);
        }
        switch (type) {
            case 3:
                holder.tv_time.setTextColor(ctx.getResources().getColor(R.color.new_font_blue));
                holder.itemLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 历次记录
                        ctx. mIndexSelectedPat = childPosition;
                        ctx. mIndexSelectedGroup = groupPosition;
                        notifyDataSetChanged();

//                        Intent i = new Intent(ctx, LisHisListActivity.class);
//                        i.putExtra("arcItemId", arcItemId);
//                        i.putExtra("PatientInfo", mPatientCurrSelected);
//                        startActivity(i);
                    }

                });
                break;
            default:
                holder.itemLinearLayout
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ctx. mIndexSelectedPat = childPosition;
                                ctx. mIndexSelectedGroup = groupPosition;
                                notifyDataSetChanged();
                                if (l.reportStatus.equals("报告已出")) {
                                    Intent i = new Intent(ctx, Lis_DetailActivity.class);
                                    i.putExtra("PatientInfo", Const.curPat);
                                    i.putExtra("ordLabNo", l.ordLabNo);
                                    ctx.startActivity(i);
                                }

                            }

                        });
                break;
        }

        holder.tv_state.setText(l.reportStatus);
        if (ctx.sort.equals("Item")) {
            holder.tv_time.setText(l.ordDate);
        } else {
            holder.tv_time.setText(l.ordItemDesc);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        try {
            size = ctx.childDataList.get(groupPosition).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public int getGroupCount() {
        return ctx.groupData.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(
                    R.layout.adapter_lis_group, null);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.groupIcon);
        if (isExpanded) {
//            iv.setImageResource(R.drawable.left_arrow_down);
            convertView.setBackgroundColor(Color.TRANSPARENT);
            // convertView.setBackgroundResource(R.drawable.list_mid_lis);
        } else {
//            iv.setImageResource(R.drawable.left_arrow_right);
            convertView.setBackgroundColor(Color.TRANSPARENT);
            // convertView.setBackgroundResource(R.drawable.list_mid_lis);
        }

        TextView title = (TextView) convertView
                .findViewById(R.id.lis_group_name);
        String d = (ctx.groupData.get(groupPosition)).get("group");
        title.setText(d);
        convertView.setClickable(false);
        return convertView;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        List<Map<String, LisReportList>> childList = ctx.childDataList
                .get(groupPosition);
        Map<String, LisReportList> map = childList.get(childPosition);
        LisReportList l = map.get("child");
        if (l.reportException.equals("1")) {
            return 1;
        } else if (l.reportException.equals("2")) {
            return 3;
        } else if (l.reportStatus == null) {
            return 0;
        } else if (l.reportStatus.equals("报告未出")) {
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public int getChildTypeCount() {
        return 4;
    }

    public class ViewHolderLis {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_state;
        public LinearLayout itemLinearLayout;
    }

}
