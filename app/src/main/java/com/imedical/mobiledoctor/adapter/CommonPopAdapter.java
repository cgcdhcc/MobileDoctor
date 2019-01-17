package com.imedical.mobiledoctor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.ActionItem;

import java.util.ArrayList;
import java.util.List;

public class CommonPopAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ActionItem> mListData = new ArrayList<ActionItem>();
    private BaseRoundActivity context;
    ViewHolder holder = null;

    public CommonPopAdapter(BaseRoundActivity context, List<ActionItem> list) {
        this.context = context;
        if (mListData != null) {
            this.mListData.addAll(list);
        }
        this.mInflater = LayoutInflater.from(context);
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

    private int selectItem = -1;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.common_pop_adapter, null);
            holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
            holder.tv_memo = (TextView) convertView.findViewById(R.id.tv_memo);
            holder.tv_success = (ImageView) convertView.findViewById(R.id.tv_success);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ActionItem b = mListData.get(position);
        holder.tv_text.setText(b.getTitle());
        if (position == context.selectPos) {
//            holder.tv_success.setVisibility(View.VISIBLE);
        } else {
//            holder.tv_success.setVisibility(View.GONE);
        }
        return convertView;
    }

    /**
     *
     */
    public class ViewHolder {
        public TextView tv_text;
        public TextView tv_memo;
        public ImageView tv_success;
    }

}

