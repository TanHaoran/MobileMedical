package com.thr.mobilemedical.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.DensityUtils;
import com.thr.mobilemedical.utils.L;

/**
 * Created by Jerry on 2016/5/17.
 */
public class NursingItemView extends RelativeLayout {

    private String name;
    private String desc;
    private int times;

    private TextView textName;
    private TextView textDesc;
    private LinearLayout checkLayout;
    private Context mContext;


    public NursingItemView(Context context) {
        this(context, null);
    }

    public NursingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NursingItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_nursing_item, this);

        textName = (TextView) findViewById(R.id.tv_name);
        textDesc = (TextView) findViewById(R.id.tv_desc);
        checkLayout = (LinearLayout) findViewById(R.id.ll_check_layout);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        textName.setText(name);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        textDesc.setText(desc);
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        L.i("读取次数" + times);
        this.times = times;
        checkLayout.removeAllViews();
        for (int i = 0; i < times; i++) {
            CheckView view = new CheckView(mContext);
            if (i == 0) {
                view.setCheck(true);
            }
            checkLayout.addView(view);
        }
    }
}
