package com.imedical.jpush.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.imedical.jpush.bean.Message;
import com.imedical.jpush.bean.MessageNoRead;
import com.imedical.jpush.bean.MsgResponse;
import com.imedical.jpush.bean.extras;
import com.imedical.jpush.service.MessageService;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.LoginHospitalActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.DialogUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.widget.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {
    public XListView lv_data;
    public String canload = "";
    public Gson g = new Gson();
    public List<Message> msglist = new ArrayList<Message>();
    MsgAdapter adapter;
    public extras extra;
    public String title, message, msgid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jpush_activity_main);
        setTitle("消息");
        if (Const.loginInfo != null) {


            lv_data = (XListView) findViewById(R.id.lv_data);
            lv_data.setPullLoadEnable(true);
            lv_data.setPullRefreshEnable(true);
            lv_data.setXListViewListener(new XListView.IXListViewListener() {

                @Override
                public void onRefresh() {
                    // TODO Auto-generated method stub
                    msglist.clear();
                    canload = "";
                    lv_data.setPullLoadEnable(true);
                    loadData();
                    lv_data.setRefreshTime(DateUtil.getNowDateTime(null));
                }

                @Override
                public void onLoadMore() {
                    // TODO Auto-generated method stub
                    loadData();
                }
            });
            lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position - 1>=0&&position - 1<msglist.size()){
                        Intent intent = new Intent(MessageActivity.this, MessageDetailActivity.class);
                        intent.putExtra("message", msglist.get(position - 1));
                        startActivity(intent);
                    }
                }
            });
            lv_data.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final String msgid = msglist.get(position - 1).msg_id;
                    DialogUtil.showAlertYesOrNo(MessageActivity.this, "操作确认", "是否删除该消息", new  MyCallback<Boolean>() {
                        @Override
                        public void onCallback(Boolean value) {
                            if (value) {
                                deletemsg(msgid);
                            }
                        }
                    });
                    return true;
                }
            });
            adapter = new MsgAdapter(this, msglist);
            lv_data.setAdapter(adapter);
            findViewById(R.id.iv_menu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MsgOperateDlg dlg = new MsgOperateDlg(MessageActivity.this);
                    dlg.show();
                }
            });
        }else{
            showToast("请先登录");
            Intent intent=new Intent(this, LoginHospitalActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void updateallmsgstatus() {
        showProgress();
        new Thread() {
            MessageNoRead mr;

            @Override
            public void run() {
                super.run();
                mr = MessageService.updateallmsgstatus();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (mr != null) {
                            if (mr.code == 0) {
                                showToast("操作成功");
                                onResume();
                            } else {
                                showToast("操作失败，请稍后再试(" + mr.msg + ")");
                            }

                        } else {
                            showToast("操作失败，请稍后再试");
                        }
                    }
                });
            }
        }.start();
    }

    public void deleteallmsg() {
        showProgress();
        new Thread() {
            MessageNoRead mr;

            @Override
            public void run() {
                super.run();
                mr = MessageService.deleteallmsg("1");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (mr != null) {
                            if (mr.code == 0) {
                                showToast("操作成功");
                                onResume();
                            } else {
                                showToast("操作失败，请稍后再试(" + mr.msg + ")");
                            }

                        } else {
                            showToast("操作失败，请稍后再试");
                        }
                    }
                });
            }
        }.start();
    }

    public void deletemsg(final String msgid) {
        showProgress();
        new Thread() {
            MessageNoRead mr;

            @Override
            public void run() {
                super.run();
                mr = MessageService.deletemsg(msgid);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (mr != null) {
                            if (mr.code == 0) {
                                showToast("操作成功");
                                onResume();
                            } else {
                                showToast("操作失败，请稍后再试(" + mr.msg + ")");
                            }

                        } else {
                            showToast("操作失败，请稍后再试");
                        }
                    }
                });
            }
        }.start();
    }

    public void loadData() {
        showProgress();
        new Thread() {
            MsgResponse response;

            @Override
            public void run() {
                super.run();
                try {
                    response = MessageService.queryMsg(canload);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (response != null&&response.code.equals("0")) {
                            canload = response.conload;
                            msglist.addAll(response.data);
                            if (response.islast) {
                                lv_data.setPullLoadEnable(false);
                            }
                            if(msglist.size()==0){
                                showNodataInListView(true);
                            }else{
                                showNodataInListView(false);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            if(response==null){
                                Toast.makeText(MessageActivity.this, "网络请求失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MessageActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                            }

                        }
                        lv_data.stopRefresh();
                        lv_data.stopLoadMore();

                    }
                });

            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        msglist.clear();
        canload = "";
        lv_data.setPullLoadEnable(true);
        loadData();
        lv_data.setRefreshTime(DateUtil.getNowDateTime(null));
    }
}
