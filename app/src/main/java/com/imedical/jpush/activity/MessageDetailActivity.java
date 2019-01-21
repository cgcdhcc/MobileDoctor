package com.imedical.jpush.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.imedical.im.activity.TalkMsgActivity;
import com.imedical.im.entity.AdmInfo;
import com.imedical.jpush.bean.Message;
import com.imedical.jpush.bean.MessageNoRead;
import com.imedical.jpush.bean.extras;
import com.imedical.jpush.service.MessageService;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.LogMe;

/**
 * Created by dashan on 2017/7/28.
 */

public class MessageDetailActivity extends BaseActivity {
    public Message message;
    public TextView tv_time,tv_content,tv_action;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message=(Message)this.getIntent().getSerializableExtra("message");
        this.setContentView(R.layout.jpush_activity_msgdetail);
        setTitle(message.title);
        LogMe.d((new Gson()).toJson(message));
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_time.setText(message.created_at);
        tv_content=(TextView)findViewById(R.id.tv_content);
        tv_content.setText(Html.fromHtml(message.content));
        tv_action=(TextView)findViewById(R.id.tv_action);
        tv_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity();
            }
        });
        if(("prompt").equals(message.extras.newsClassification)){
            tv_action.setVisibility(View.GONE);
        }else{
            tv_action.setVisibility(View.VISIBLE);
        }
        if(("1").equals(message.markread)){
            setIsRead(message.msg_id);
        }

    }
    public void toActivity(){
        extras sysMsg=message.extras;
        Intent intent;
        switch(sysMsg.newsClassification){
            case "message":
                intent=new Intent(MessageDetailActivity.this,WebViewActivity.class);
                intent.putExtra("url",sysMsg.actionUrl);
                startActivity(intent);
                break;
            case "business":

                switch (sysMsg.actionCode){

                    case "PAT_REPLY":// : 消息回复
                        intent=new Intent(MessageDetailActivity.this, TalkMsgActivity.class);
                        intent.putExtra("admInfo",new AdmInfo(
                                sysMsg.jumpData.get("patName").toString(),
                                sysMsg.jumpData.get("admId").toString(),
                                sysMsg.jumpData.get("patSex").toString(),
                                sysMsg.jumpData.get("patAge").toString(),
                                sysMsg.jumpData.get("patAvatarUrl").toString(),
                                sysMsg.jumpData.get("docMarkId").toString(),
                                sysMsg.jumpData.get("doctorName").toString(),
                                sysMsg.jumpData.get("doctorTitle").toString(),
                                sysMsg.jumpData.get("doctorCode").toString(),
                                sysMsg.jumpData.get("doctorPicUrl").toString(),
                                sysMsg.jumpData.get("chatStatus").toString()
                        ));
                        startActivity(intent);
                        break;
                    case "Ris":// : 检查

                        break;
                    case "Pay":// : 缴费

                        break;
                    case "Paid"://缴费成功
                       break;
                    default:
                        break;
                }
                break;
            case "prompt":
                break;
            default: break;
        }

    }
    public void setIsRead(final String msgid){

        new Thread(){
            @Override
            public void run() {
                super.run();
                MessageNoRead mr= MessageService.updatemsgstatus(msgid,"0");//所置状态(0=>已读,1=>未读),默认不传为0,将消息置为已读
                if(mr!=null){
                    LogMe.d("msg","处理结果"+mr.msg);
                }
            }
        }.start();
    }
}
