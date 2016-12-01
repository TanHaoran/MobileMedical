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

import java.util.List;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 滑动选择弹出框
 * @date 2015-10-8 11:26:40
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class SelectPopupWindow extends PopupWindow {

    @SuppressWarnings("unused")
    private Context mContext;

    private View view;

    private TextView mTextTitle;
    private TextView mTextEnsure;
    private TextView mTextCancle;
    private WheelView mWheel;

    private int mIndex = -1;
    private List<String> mItems;

    private OnEnsureClickListener ensureClickListener;

    public interface OnEnsureClickListener {
        void onEnsureClick();
    }

    /**
     * 设置确定点击事件
     *
     * @param listener
     */
    public void setOnEnsureClickListener(OnEnsureClickListener listener) {
        ensureClickListener = listener;
    }

    /**
     * 获取滑动选择框内容
     *
     * @return
     */
    public String getWheelString() {
        return mWheel.getSeletedItem();
    }

    public SelectPopupWindow(Context context) {
        super();
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_selection, null);
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

        mTextTitle = (TextView) view.findViewById(R.id.tv_title);
        mTextEnsure = (TextView) view.findViewById(R.id.tv_ensure);
        mTextCancle = (TextView) view.findViewById(R.id.tv_cancle);
        mWheel = (WheelView) view.findViewById(R.id.wv_selection);

        mTextEnsure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mIndex = mWheel.getSeletedIndex();
                ensureClickListener.onEnsureClick();
                dismiss();
            }
        });
        mTextCancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mWheel.setOffset(2);
        // 如果之前选择好的，弹出式就显示之前选择的框
        if (mIndex == -1) {
            mWheel.setSeletion(0);
        } else {
            mWheel.setSeletion(mIndex);
        }

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
     * @param title
     */
    public void setTitle(String title) {
        mTextTitle.setText(title);
    }

    /**
     * 设置标题文字
     *
     * @param resId
     */
    public void setTitle(int resId) {
        mTextTitle.setText(resId);
    }

    public List<String> getItems() {
        return mItems;
    }

    public void setItems(List<String> items) {
        mWheel.setItems(items);
        mItems = items;
    }

    public int getSelectIndex() {
        return mWheel.getSeletedIndex();
    }

    public void setSelection(int index) {
        mWheel.setSeletion(index);
    }

    /**
     * 在屏幕的最低端显示
     */
    public void show(View v) {
        showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

}
