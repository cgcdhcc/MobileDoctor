package com.imedical.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.im.entity.AdmInfo;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.DocOrderService;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.OETabs;

import java.util.ArrayList;
import java.util.List;

public class OETabsActivity extends BaseActivity {
    public ListView lv_data1, lv_data2;
    public LinearLayout view_recorder;
    private int screenWidth = 0;
    private int currentPage = 0;
    public List<OETabs> list1 = new ArrayList<OETabs>();
    public List<OETabs> list2 = new ArrayList<OETabs>();
    public int currentOETabs = 0;
    public OETabs2Adapter adapter;
    private AdmInfo tempAI;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.docorder_model);
        setTitle("医嘱模板");
        tempAI=(AdmInfo)this.getIntent().getSerializableExtra("tempAI");
        lv_data1 = (ListView) findViewById(R.id.lv_data1);
        lv_data2 = (ListView) findViewById(R.id.lv_data2);
        view_recorder = (LinearLayout) findViewById(R.id.view_recorder);
        adapter = new OETabs2Adapter();
        lv_data1.setAdapter(adapter);
        lv_data1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentOETabs = position;
                if (list2.get(position).list.size() == 0) {
                    loadOETab3(list2.get(position));
                } else {
                    lv_data2.setAdapter(new OETabs3Adapter());
                }
                adapter.notifyDataSetChanged();
            }
        });
        lv_data2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String arcItemId = list2.get(currentOETabs).list.get(position).arcRowId;
                Intent intent=new Intent(OETabsActivity.this,DocOrderActivity.class);
                intent.putExtra("arcItemId", arcItemId);
                intent.putExtra("tempAI",tempAI);
                startActivity(intent);
            }
        });
        loadOETab1();
    }

    public void intiRecord() {
        view_recorder.removeAllViews();
        for (int i = 0; i < list1.size(); i++) {
            final int position = i;
            View view;
            if (currentPage == position) {
                view = getLayoutInflater().inflate(R.layout.docorder_item_oetab1_checked, null);
            } else {
                view = getLayoutInflater().inflate(R.layout.docorder_item_oetab1, null);
            }
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_desc.setText(list1.get(position).tabName);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPage = position;
                    currentOETabs=0;
                    loadOETab2();
                    intiRecord();
                }
            });
            view_recorder.addView(view);

        }
    }

    class OETabs2Adapter extends BaseAdapter {
        public OETabs2Adapter() {

        }

        @Override
        public int getCount() {
            return list2.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.docorder_item_otetab2, null);
            View view_ote=view.findViewById(R.id.view_ote);
            if(currentOETabs==i){
//                view_checked.setVisibility(View.VISIBLE);
                view_ote.setBackgroundColor(getResources().getColor(R.color.white));
            }else{
//                view_checked.setVisibility(View.GONE);
                view_ote.setBackgroundColor(getResources().getColor(R.color.background));
            }
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_desc.setText(list2.get(i).subTabName);
            return view;
        }
    }

    class OETabs3Adapter extends BaseAdapter {
        public OETabs3Adapter() {

        }

        @Override
        public int getCount() {
            return list2.get(currentOETabs).list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.docorder_item_otetab3, null);
            TextView tv_des = (TextView) view.findViewById(R.id.tv_desc);
            tv_des.setText(list2.get(currentOETabs).list.get(i).arcDesc);
            return view;
        }
    }

    public void loadOETab1() {
        list1.clear();
        showProgress();
        new Thread() {
            List<OETabs> list;

            @Override
            public void run() {
                super.run();
                try {
                    list = DocOrderService.loadOETabs(Const.loginInfo.userCode,tempAI.admId, Const.loginInfo.defaultGroupId, Const.loginInfo.defaultDeptId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (list != null) {
                            list1.addAll(list);
                            intiRecord();
                            if (list1.size() > 0) {
                                loadOETab2();
                            }
                        } else {
                            showToast("加载医嘱标签模板失败");
                        }
                    }
                });
            }
        }.start();

    }

    public void loadOETab2() {
        list2.clear();
        showProgress();
        new Thread() {
            List<OETabs> list;

            @Override
            public void run() {
                super.run();
                try {
                    list = DocOrderService.loadOETabs_2(Const.loginInfo.userCode, tempAI.admId, Const.loginInfo.defaultGroupId, Const.loginInfo.defaultDeptId, list1.get(currentPage).tabId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        dismissProgress();
                        if (list != null) {
                            list2.addAll(list);
                            adapter.notifyDataSetChanged();
                            if(list2.size()>0){
                                 loadOETab3(list2.get(currentOETabs));
                            }
                        } else {
                            showToast("加载医嘱标签模板失败");
                        }
                    }
                });
            }
        }.start();

    }

    public void loadOETab3(final OETabs oets) {
        showProgress();
        new Thread() {
            List<OETabs> list;

            @Override
            public void run() {
                super.run();
                try {
                    list = DocOrderService.loadArciItemByTab2(Const.loginInfo.userCode,tempAI.admId, Const.loginInfo.defaultGroupId, Const.loginInfo.defaultDeptId, list1.get(currentPage).tabId, oets.subTabId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        dismissProgress();
                        if (list != null) {
                            oets.list = list;
                            lv_data2.setAdapter(new OETabs3Adapter());
                        } else {
                            showToast("加载医嘱标签模板失败");
                        }
                    }
                });
            }
        }.start();

    }
}
