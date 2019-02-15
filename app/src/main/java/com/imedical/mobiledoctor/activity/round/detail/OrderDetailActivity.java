package com.imedical.mobiledoctor.activity.round.detail;

import android.os.Bundle;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.OrderItem;

public class OrderDetailActivity extends BaseActivity {
    public OrderItem orderItem;
    public TextView tv_orderStatus,tv_priority,tv_doseQty,tv_frequency,tv_instruction,tv_duration,tv_orderDoctor,tv_ordStartDate
            ,tv_ordStartTime,tv_stopOrderDoctor,tv_stopOrderDate,tv_stopOrderTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_order_detail);

        orderItem=(OrderItem)this.getIntent().getSerializableExtra("orderItem");
        setTitle(orderItem.itemDesc);
        tv_orderStatus=findViewById(R.id.tv_orderStatus);
        tv_orderStatus.setText(orderItem.orderStatus==null?"":orderItem.orderStatus);
        tv_priority=findViewById(R.id.tv_priority);
        tv_priority.setText(orderItem.priority==null?"":orderItem.priority);
        tv_doseQty=findViewById(R.id.tv_doseQty);
        tv_doseQty.setText((orderItem.doseQty==null?"":orderItem.doseQty)+(orderItem.doseUom==null?"":orderItem.doseUom));
        tv_frequency=findViewById(R.id.tv_frequency);
        tv_frequency.setText(orderItem.frequency==null?"":orderItem.frequency);
        tv_instruction=findViewById(R.id.tv_instruction);
        tv_instruction.setText(orderItem.instruction==null?"":orderItem.instruction);
        tv_duration=findViewById(R.id.tv_duration);
        tv_duration.setText(orderItem.duration==null?"":orderItem.duration);

        tv_orderDoctor=findViewById(R.id.tv_orderDoctor);
        tv_orderDoctor.setText(orderItem.orderDoctor==null?"":orderItem.orderDoctor);

        tv_ordStartDate=findViewById(R.id.tv_ordStartDate);
        tv_ordStartDate.setText(orderItem.ordStartDate==null?"":orderItem.ordStartDate);

        tv_ordStartTime=findViewById(R.id.tv_ordStartTime);
        tv_ordStartTime.setText(orderItem.ordStartTime==null?"":orderItem.ordStartTime);

        tv_stopOrderDoctor=findViewById(R.id.tv_stopOrderDoctor);
        tv_stopOrderDoctor.setText(orderItem.stopOrderDoctor==null?"":orderItem.stopOrderDoctor);

        tv_stopOrderDate=findViewById(R.id.tv_stopOrderDate);
        tv_stopOrderDate.setText(orderItem.stopOrderDate==null?"":orderItem.stopOrderDate);

        tv_stopOrderTime=findViewById(R.id.tv_stopOrderTime);
        tv_stopOrderTime.setText(orderItem.stopOrderTime==null?"":orderItem.stopOrderTime);
    }
}
