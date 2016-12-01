package com.thr.mobilemedical.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.thr.mobilemedical.R;

/**
 * @description 修改头像弹出框
 * @date 2015-9-29 16:44:26
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class ChangeheadPopupWindow extends PopupWindow {

	@SuppressWarnings("unused")
	private Context mContext;

	/**
	 * 主View
	 */
	private View view;

	public ChangeheadPopupWindow(Context context) {
		super();
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.pop_changehead, null);
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
		// 监听当点击弹窗狂外的屏幕弹出框就会消失
		view.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = view.findViewById(R.id.ll_main).getTop();
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

}
