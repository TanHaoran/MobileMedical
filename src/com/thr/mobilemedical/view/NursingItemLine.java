package com.thr.mobilemedical.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.NursingItem;

/**
 * Created by Jerry on 2016/5/18.
 */
public class NursingItemLine extends RelativeLayout {

    private Context mContext;

    private TextView textTimes;
    private TextView textName;
    private TextView textDesc;
    private LinearLayout checkLayout;

    private NursingItem nursingItem;



    public NursingItemLine(Context context) {
        this(context, null);
    }

    public NursingItemLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NursingItemLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_nursing_item_line, this);

        textName = (TextView) findViewById(R.id.tv_name);
        textDesc = (TextView) findViewById(R.id.tv_desc);
        textTimes = (TextView) findViewById(R.id.tv_times);
        checkLayout = (LinearLayout) findViewById(R.id.ll_check);
    }

    public NursingItem getNursingItem() {
        return nursingItem;
    }

    public void setNursingItem(NursingItem nursingItem) {
        this.nursingItem = nursingItem;
        textName.setText(nursingItem.getName());
        textDesc.setText(nursingItem.getDesc());
        textTimes.setText(String.valueOf(nursingItem.getTimes()));
        setTimes(nursingItem.getTimes());
    }

    public void setTimes(int times) {
        for (int i = 0; i < times; i++) {
            CheckView cv = new CheckView(mContext);
            cv.setCheck(true);
            checkLayout.addView(cv);
        }
    }

}
