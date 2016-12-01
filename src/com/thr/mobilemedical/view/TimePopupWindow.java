package com.thr.mobilemedical.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 时间输入弹出框
 * @date 2015-10-9 13:46:30
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class TimePopupWindow extends PopupWindow {

    private TextView mEnsure;
    private TextView mCancle;

    private WheelView mHour;
    private WheelView mMinute;

    List<String> hours = new ArrayList<String>();
    List<String> minutes = new ArrayList<String>();

    private View view;

    private SureClickListener sureClickListener;

    public void setSureClickListener(SureClickListener sureClickListener) {
        this.sureClickListener = sureClickListener;
    }

    public interface SureClickListener {
        void onSureClick();
    }

    public TimePopupWindow(Context context) {
        super();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_time, null);
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        // 设置出入动画
        setAnimationStyle(R.style.pop_changehead);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);
        initView();
    }

    /**
     * 初始化显示和监听器
     */
    private void initView() {

        mEnsure = (TextView) view.findViewById(R.id.tv_ensure);
        mCancle = (TextView) view.findViewById(R.id.tv_cancle);
        mHour = (WheelView) view.findViewById(R.id.wv_hour);
        mMinute = (WheelView) view.findViewById(R.id.wv_minute);

        initWheelView();

        mEnsure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sureClickListener != null) {
                    sureClickListener.onSureClick();
                }
                dismiss();
            }
        });
        mCancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.ll_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 初始化两个时间选择框
     */
    private void initWheelView() {
        mHour.setSeletion(0);
        mMinute.setSeletion(0);

        mHour.setOffset(2);
        mMinute.setOffset(2);

        for (int i = 0; i < 24; i++) {
            StringBuilder hour = new StringBuilder();
            if (i < 10) {
                hour.append("0" + i);
            } else {
                hour.append(i);
            }
            hours.add(hour.toString());
            mHour.setItems(hours);
        }

        for (int i = 0; i < 60; i++) {
            StringBuilder minute = new StringBuilder();
            if (i < 10) {
                minute.append("0" + i);
            } else {
                minute.append(i);
            }
            minutes.add(minute.toString());
        }
        mMinute.setItems(minutes);

    }

    /**
     * 取得目前正在显示的内容
     */
    public String getContent() {
        return DateUtil.getYMD() + " " + mHour.getSeletedItem() + ":"
                + mMinute.getSeletedItem() + ":00";
    }

    public WheelView getHourWheel() {
        return mHour;
    }

    public WheelView getMinuteWheel() {
        return mMinute;
    }

    /**
     * 根据默认时间显示
     * @param v
     */
    public void show(View v) {
        showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        int h = DateUtil.getCurrentHour();
        int m = DateUtil.getCurrentMinute();

        mHour.setSeletion(h);
        mMinute.setSeletion(m);
    }

    /**
     * 根据指定时间显示
     * @param v
     * @param h
     * @param m
     */
    public void show(View v, int h, int m) {
        showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        mHour.setSeletion(h);
        mMinute.setSeletion(m);
    }


}
