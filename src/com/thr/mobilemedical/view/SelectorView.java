package com.thr.mobilemedical.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thr.mobilemedical.R;

/**
 * Created by Jerry on 2016/5/16.
 */
public class SelectorView extends LinearLayout {
    
    private LinearLayout mLeftLayout;
    private TextView mLeftText;
    private View mLeftLine;

    private LinearLayout mRightLayout;
    private TextView mRightText;
    private View mRightLine;

    
    private OnSelectorClickListener mSelectorClickListener;
    
    public interface OnSelectorClickListener {
        void onLeftClick();
        void onRightClick();
    }

    /**
     * 设置选择条的点击监听事件
     * @param onSelectorClickListener
     */
    public void setOnSelectorClickListener(OnSelectorClickListener onSelectorClickListener) {
        mSelectorClickListener = onSelectorClickListener;
    }


    public SelectorView(Context context) {
        this(context, null);
    }

    public SelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_selector, this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelectorView);

        // 取得自定义的属性
        String left = ta.getString(R.styleable.SelectorView_leftSelect);
        String right = ta.getString(R.styleable.SelectorView_rightSelect);


        mLeftLayout = (LinearLayout) findViewById(R.id.ll_left);
        mLeftText = (TextView) findViewById(R.id.tv_left);
        mLeftLine = findViewById(R.id.v_left);


        mRightLayout = (LinearLayout) findViewById(R.id.ll_right);
        mRightText = (TextView) findViewById(R.id.tv_right);
        mRightLine = findViewById(R.id.v_right);

        mLeftText.setText(left);
        mRightText.setText(right);

        mLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLeftText.setTextColor(getResources().getColor(R.color.login_btn_bg_normal));
                mRightText.setTextColor(getResources().getColor(R.color.bottom_tab_text));
                mLeftLine.setVisibility(VISIBLE);
                mRightLine.setVisibility(INVISIBLE);
                if (mSelectorClickListener != null) {
                    mSelectorClickListener.onLeftClick();
                }
            }
        });

        mRightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mRightText.setTextColor(getResources().getColor(R.color.login_btn_bg_normal));
                mLeftText.setTextColor(getResources().getColor(R.color.bottom_tab_text));
                mRightLine.setVisibility(VISIBLE);
                mLeftLine.setVisibility(INVISIBLE);
                if (mSelectorClickListener != null) {
                    mSelectorClickListener.onRightClick();
                }
            }
        });
    }

    /**
     * 设置左边的文字
     * @param title
     */
    public void setLeftText(String title) {
        mLeftText.setText(title);
    }

    /**
     * 设置右边的文字
     * @param title
     */
    public void setRightText(String title) {
        mRightText.setText(title);
    }

}
