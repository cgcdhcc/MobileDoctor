package com.imedical.mobiledoctor.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;

public class TabView extends LinearLayout {
  TextView  tabview_text,tabview_line;
  OnClickListener listener;
  Context ctx;
    public TabView(Context context,String Title,OnClickListener l) {
        super(context);
        this.ctx=context;
        this.listener=l;
        View view=LayoutInflater.from(context).inflate(R.layout.tab_view, this, true);
        tabview_text =(TextView)view.findViewById(R.id.tabview_text);
        tabview_line =(TextView)view.findViewById(R.id.tabview_line);
        view.setOnClickListener(listener);
        tabview_text.setText(Title);
    }

    public void SetFoucse(){
        tabview_text.setTextColor(ctx.getResources().getColor(R.color.mobile_blue));
        tabview_line.setBackground(ctx.getDrawable(R.color.mobile_blue));
    }
    public void SetUnFoucse(){
        tabview_text.setTextColor(ctx.getResources().getColor(R.color.gray));
        tabview_line.setBackground(ctx.getDrawable(R.color.white));
    }
}
