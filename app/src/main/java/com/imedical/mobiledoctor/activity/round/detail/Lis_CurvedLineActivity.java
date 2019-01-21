package com.imedical.mobiledoctor.activity.round.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.LisReportItemManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LisReportItem;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lis_CurvedLineActivity extends BaseActivity {
    private String mInfo = "";
    private View btn_back;
    private String userCode;
    private String admId;
    private String ordLabNo;
    private String itemCode;
    private float maxY,maxHeight;
    private float bottom,top;
    private List<LisReportItem> mListData = new ArrayList<LisReportItem>();

    private GraphicalView chart;
    private LinearLayout layout;
    private TextView tv_my;
    private TextView tv_lis_unit;
    private ImageView iv_left;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.curve_line);
        setTitle("检验结果波形图");
        initUi();
        //获取传过来的参数
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        userCode = bundle.getString("userCode");
        admId = bundle.getString("admId");
        ordLabNo = bundle.getString("ordLabNo");
        itemCode = bundle.getString("itemCode");
        top = bundle.getFloat("top");
        bottom = bundle.getFloat("bottom");
        maxY = top ;

        loadDataThread();
    }


    private void setMaxY(){
        for (int i = 0; i < mListData.size(); i ++) {
            Float val = Float.parseFloat(mListData.get(i).resultValue);
            if(val > maxY) {
                maxY = val;
            }
        }
        if (maxY <= 1) {
            maxHeight = 1;
        }else if (maxY <= 5) {
            maxHeight = 5;
        }else if (maxY <= 10) {
            maxHeight = 10;
        }else if (maxY <= 100) {
            maxHeight = maxY - ((int)maxY%10) + 10;
        }else if (maxY <= 1000) {
            maxHeight = maxY - ((int)maxY%100) + 100;
        }else if (maxY <= 10000) {
            maxHeight = maxY - ((int)maxY%1000) + 1000;
        }else if (maxY <= 100000) {
            maxHeight = maxY - ((int)maxY%10000) + 10000;
        }

    }

    private void createGraph() {
        setMaxY();
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        //设置图形不可移动
//		renderer.setPanEnabled(true, false);
        //x\y取值范围
        renderer.setPanLimits(new double[] {0, mListData.size(), 0, maxY });//设置拖动时X轴Y轴允许的最大值最小值.
        renderer.setZoomLimits(new double[] {0, mListData.size(), 0, maxY });//设置放大缩小时X轴Y轴允许的最大最小值.
        //设置背景色
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setMarginsColor(Color.WHITE);

        renderer.setYAxisMax(maxHeight);//设置y轴最大值
        renderer.setYAxisMin(0);//设置y轴最小值
        renderer.setYLabels(10);//设置y轴刻度个数
        renderer.setXLabels(0);//设置x轴刻度个数
        renderer.setXAxisMax(15);//设置x轴最大值
        renderer.setAxesColor(Color.RED);//设置坐标轴颜色
        renderer.setAxisTitleTextSize(10);//设置坐标轴文字大小
        renderer.setLabelsTextSize(18);//设置轴标签文字大小
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        //数据线
        XYSeries series = new XYSeries("数据线");
        for (int i = 0; i < mListData.size(); i ++) {
            renderer.addTextLabel(i, mListData.get(i).reportDate);
            series.add(i, Double.parseDouble(mListData.get(i).resultValue));
        }

        dataset.addSeries(series);
        //参考线
        XYSeries series1 = new XYSeries("参考线");
        series1.add(0, bottom);
        series1.add(mListData.size() - 1, bottom);
        dataset.addSeries(series1);

        XYSeries series2 = new XYSeries("");
        series2.add(0, top);
        series2.add(mListData.size() - 1, top);
        dataset.addSeries(series2);

        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        xyRenderer.setColor(Color.BLUE);
        xyRenderer.setPointStyle(PointStyle.SQUARE);
        renderer.addSeriesRenderer(xyRenderer);

        XYSeriesRenderer xyRenderer1 = new XYSeriesRenderer();
        xyRenderer1.setColor(Color.GREEN);
        renderer.addSeriesRenderer(xyRenderer1);

        XYSeriesRenderer xyRenderer2 = new XYSeriesRenderer();
        xyRenderer2.setColor(Color.GREEN);
        renderer.addSeriesRenderer(xyRenderer2);
        tv_my.setText(mListData.get(0).itemDesc + "-检验结果");
        tv_lis_unit.setText("单位：" + mListData.get(0).unit);
        chart = ChartFactory.getLineChartView(getApplicationContext(), dataset, renderer);
        layout.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void loadDataThread(){
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    Map map = new HashMap();
                    map.put("userCode", userCode);
                    map.put("admId",admId);
                    map.put("ordLabNo", ordLabNo);
                    map.put("itemCode", itemCode);
                    List<LisReportItem> list = LisReportItemManager.listLisReportCurve(map);
                    if (list.size() != 0) {
                        mListData.clear();
                        mListData.addAll(list);
                        list.clear();
                        list = null ;
                        message.what = 0;
                    } else {
                        message.what = 2;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                    message.what = 1;
                } finally {
                    myHandler.sendMessage(message);
                }
            }
        }.start();
    }

    Handler myHandler = new Handler() {
        public void handleMessage (Message msg){
            switch(msg.what) {
                case 0:
                    createGraph();
                    break;
                case 1:
                    Toast.makeText(getApplication(), mInfo, Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(getApplication(), "暂无消息", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    private void initUi() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_my = (TextView) findViewById(R.id.tv_record);
        if (iv_left != null) {
            iv_left.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    finish();
                }

            });
        }
        tv_lis_unit = (TextView)findViewById(R.id.tv_unit);
        layout      = (LinearLayout)findViewById(R.id.curveline);
    }


}
