package com.thr.mobilemedical.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thr.mobilemedical.R;

/**
 * Created by Jerry on 2016/5/17.
 */
public class CheckView extends LinearLayout {

    private ImageView imageView;

    private boolean isCheck = false;

    public CheckView(Context context) {
        this(context, null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_check_view, this);
        imageView = (ImageView) findViewById(R.id.img);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckView);
        isCheck = ta.getBoolean(R.styleable.CheckView_isCheck, false);

        if (isCheck) {
            imageView.setImageResource(R.drawable.check);
        } else {
            imageView.setImageResource(R.drawable.round_gray);
        }


    }

    public boolean isCheck() {
        return isCheck;
    }

    /**
     * 设置是否执行
     * @param check
     */
    public void setCheck(boolean check) {
        isCheck = check;
        imageView.setImageResource(R.drawable.check);
    }
}
