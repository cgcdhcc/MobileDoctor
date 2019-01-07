package com.imedical.mobiledoctor.util;

import android.app.DatePickerDialog;
import android.content.Context;

public class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(Context context,
                              OnDateSetListener callBack, int year, int monthOfYear,
                              int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        this.setButton("取消", (OnClickListener) null);
        this.setButton2("确定", this);
        // TODO Auto-generated constructor stub

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        //super.onStop();
    }

}
