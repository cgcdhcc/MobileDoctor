package com.imedical.im.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.im.activity.DocOrderActivity;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.entity.Priority;

import java.util.List;


public class PriorityOperateDlg extends Dialog {
    public DocOrderActivity activity;
    public List<Priority> tempList;
    public ListView lv_data;
    public PriorityOperateDlg(DocOrderActivity activity, List<Priority> tempList) {
        super(activity, R.style.quick_option_dialog);
        this.activity=activity;
        this.tempList=tempList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //获得dialog的window窗口
        Window window = getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        this.setContentView(R.layout.docorder_priority_dlg);
        lv_data=(ListView) findViewById(R.id.lv_data);
        lv_data.setAdapter(new PriorityAdapter());
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dismiss();
                activity.setPriority(tempList.get(i));
            }
        });
    }
    class PriorityAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return tempList.size();
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
            view=activity.getLayoutInflater().inflate(R.layout.docorder_item_text,null);
            TextView tv_value=(TextView)view.findViewById(R.id.tv_value);
            tv_value.setText(tempList.get(i).oecprDesc);
            return view;
        }
    }
}
