package com.thr.mobilemedical.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.InternetUtil;

/**
 * 标题栏自定义控件
 *
 * @author Jerry
 */
public class TitleBar extends RelativeLayout {

    private RelativeLayout mLeftLayout;
    private RelativeLayout mRightLayout;

    private TextView mTitle;
    private TextView mLeft;
    private TextView mRight;

    private ImageView mLeftImg;
    private ImageView mRightImg;

    private OnLeftClickListener leftClickListener;
    private OnRightClickListener rightClickListener;

    private Context mContext;

    @SuppressLint("Recycle")
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_titlebar, this);

        mLeftLayout = (RelativeLayout) findViewById(R.id.rl_left);
        mRightLayout = (RelativeLayout) findViewById(R.id.rl_right);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mLeft = (TextView) findViewById(R.id.tv_left);
        mRight = (TextView) findViewById(R.id.tv_right);
        mLeftImg = (ImageView) findViewById(R.id.iv_left);
        mRightImg = (ImageView) findViewById(R.id.iv_right);


        // 取得自定义的属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String left = ta.getString(R.styleable.TitleBar_leftText);
        String title = ta.getString(R.styleable.TitleBar_titleText);
        String right = ta.getString(R.styleable.TitleBar_rightText);
        boolean leftVisible = ta.getBoolean(R.styleable.TitleBar_leftVisible, true);
        boolean rightVisible = ta.getBoolean(R.styleable.TitleBar_rightVisible, true);

        int leftResource = ta.getResourceId(R.styleable.TitleBar_leftImage, R.drawable.top_list);
        int rightResource = ta.getResourceId(R.styleable.TitleBar_rightImage, R.drawable.top_bh);

        mLeft.setText(left);
        mTitle.setText(title);
        mRight.setText(right);
        if (!leftVisible) {
            mLeftImg.setVisibility(INVISIBLE);
        }
        if (!rightVisible) {
            mRightImg.setVisibility(INVISIBLE);
        }

        mLeftImg.setImageResource(leftResource);
        mRightImg.setImageResource(rightResource);

        mLeftLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (leftClickListener != null) {
                    leftClickListener.onLeftClick(v);
                }
            }
        });
        mRightLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (InternetUtil.isNetworkConnected(mContext)) {
                    if (rightClickListener != null) {
                        rightClickListener.onRightClick(v);
                    }
                }
            }
        });

    }

    /**
     * 设置标题内容
     *
     * @param title 字符串内容
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * 设置标题内容
     *
     * @param resid 资源id
     */
    public void setTitle(int resid) {
        mTitle.setText(resid);
    }

    /**
     * 设置左侧内容
     *
     * @param title 字符串内容
     */
    public void setLeftText(String title) {
        mLeft.setText(title);
    }

    /**
     * 设置左侧内容
     *
     * @param resid 资源id
     */
    public void setLeftText(int resid) {
        mLeft.setText(resid);
    }

    /**
     * 设置右侧内容
     *
     * @param title 字符串内容
     */
    public void setRightText(String title) {
        mRight.setText(title);
    }

    /**
     * 设置右侧内容
     *
     * @param resid 资源id
     */
    public void setRightText(int resid) {
        mRight.setText(resid);
    }

    public void setOnLeftClickListener(OnLeftClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
    }

    public void setOnRightClickListener(OnRightClickListener rightClickListener) {
        this.rightClickListener = rightClickListener;
    }

    public void setLeftImage(int resId) {
        mLeftImg.setImageResource(resId);
    }

    public void setRightImage(int resId) {
        mRightImg.setImageResource(resId);
    }

    public interface OnLeftClickListener {
        void onLeftClick(View v);
    }

    public interface OnRightClickListener {
        void onRightClick(View v);
    }

}
