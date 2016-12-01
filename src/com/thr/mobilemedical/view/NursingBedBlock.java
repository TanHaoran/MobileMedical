package com.thr.mobilemedical.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.NursingBed;
import com.thr.mobilemedical.bean.NursingItem;

import java.util.List;

/**
 * Created by Jerry on 2016/5/18.
 */
public class NursingBedBlock extends LinearLayout {

    private Context mContext;

    private TextView textBed;
    private TextView textName;
    private TextView textTimes;
    private LinearLayout nursingLayout;

    private List<NursingItem> nursingList;

    private NursingBed nursingBed;



    public NursingBedBlock(Context context) {
        this(context, null);
    }

    public NursingBedBlock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NursingBedBlock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_nursing_bed_block, this);

        nursingLayout = (LinearLayout)findViewById(R.id.ll_nursing);
        textBed = (TextView)findViewById(R.id.tv_bed);
        textName = (TextView)findViewById(R.id.tv_name);
        textTimes = (TextView)findViewById(R.id.tv_times);
    }

    public List<NursingItem> getNursingList() {
        return nursingList;
    }

    public void setNursingList(List<NursingItem> nursingList) {
        this.nursingList = nursingList;
        nursingLayout.removeAllViews();
        for(NursingItem item: nursingList) {
            NursingItemLine line = new NursingItemLine(mContext);
            line.setNursingItem(item);
            nursingLayout.addView(line);
        }
    }


    public NursingBed getNursingBed() {
        return nursingBed;
    }

    public void setNursingBed(NursingBed nursingBed) {
        this.nursingBed = nursingBed;
        textBed.setText(nursingBed.getId());
        textName.setText(nursingBed.getName());
        int times = 0;
        for(NursingItem item :nursingBed.getNursingList()) {
            times += item.getTimes();
        }
        textTimes.setText(String.valueOf(times));
        setNursingList(nursingBed.getNursingList());
    }
}
