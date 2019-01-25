package com.imedical.mobiledoctor.activity.round;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.BrowseLocation;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.widget.TabView;

import java.util.List;

public class MultimediaActivity extends BaseRoundActivity {
    private String mInfo = "error!";
    private BrowseLocation mBrowseLocation=null;
    private LayoutInflater mInflater;
    private LinearLayout layout_btn;
    private View scr_browse ;
    private WebView webView_case_report=null;
    private LinearLayout ll_nodata;
    private List<TabView> listBtn;
    private int oldSel=-1;
    private ImageView iv_nore = null ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page8_multimedia_activity);
        InitViews();
//        InitRecordListAndPatientList(MultimediaActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SWITHC_CODE) {
        }
        setInfos(Const.curPat.patName,(Const.curPat.bedCode==null?"":Const.curPat.bedCode)+"床("+(Const.curPat.patRegNo==null?"":Const.curPat.patRegNo)+")");//更新姓名，床号
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void OnPatientSelected(PatientInfo p) {
        Intent it0 =new Intent(MultimediaActivity.this,PatientListActivity.class);
        startActivityForResult(it0, SWITHC_CODE);
    }

    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
    }


    private void InitViews() {
        setTitle("多媒体病历");

    }



    public View.OnClickListener btnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (Integer) v.getTag();

        }

    };
}
