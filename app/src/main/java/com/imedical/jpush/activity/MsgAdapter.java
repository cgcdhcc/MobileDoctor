package com.imedical.jpush.activity;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.imedical.jpush.bean.Message;
import com.imedical.mobiledoctor.R;

import java.util.List;

/**
 * Created by dashan on 2017/7/6.
 */

public class MsgAdapter extends BaseAdapter {
    public Activity activity;
    public List<Message> msglist;
    public MsgAdapter(Activity activity, List<Message> msglist){
          this.activity=activity;
        this.msglist=msglist;
    }
    @Override
    public int getCount() {
        return msglist.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=activity.getLayoutInflater().inflate(R.layout.jpush_item_msg,null);
        TextView tv_title=(TextView)convertView.findViewById(R.id.tv_title);
        Message msg=msglist.get(position);
        tv_title.setText(msglist.get(position).title);
        TextView tv_content=(TextView)convertView.findViewById(R.id.tv_content);
        tv_content.setText(Html.fromHtml(msglist.get(position).content));
        TextView tv_created_at=(TextView)convertView.findViewById(R.id.tv_created_at);
        tv_created_at.setText(msglist.get(position).created_at);
        ImageView iv_marked=(ImageView)convertView.findViewById(R.id.iv_marked);
        if(msglist.get(position).markread.equals("0")){
            iv_marked.setVisibility(View.GONE);
        }else{
            iv_marked.setVisibility(View.VISIBLE);
        }
        ImageView iv_type=(ImageView)convertView.findViewById(R.id.iv_type);
        String ActionCode=msg.extras.actionCode==null?"":msg.extras.actionCode;
        if(ActionCode.equals("DOC_VIDEO_REGIST")||ActionCode.equals("DOC_VIDEO_REGIST")||ActionCode.equals("DOC_VIDEO_REGIST")){
            iv_type.setImageResource(R.drawable.msg_video);
        }else {
            iv_type.setImageResource(R.drawable.icon_common_head);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    public int getImgIcon(String msgGroup){
        if(msgGroup==null){
            return R.drawable.icon;
        }
        switch(msgGroup){
            case "report":
                return R.drawable.icon;
            case "normal":
                return R.drawable.icon;
            case "appRemaind":
                return R.drawable.icon;
            case "feeRemaind":
                return R.drawable.icon;
            case "importRemaind":
                return R.drawable.icon;
            default:
                return R.drawable.icon;
        }
    }
}
