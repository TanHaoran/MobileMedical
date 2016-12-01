package com.thr.mobilemedical.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.ScreenUtils;

/**
 * @description 左侧菜单
 * @date 2015-9-24 13:45:29
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class PatientPopupWindow extends PopupWindow {

	private Context mContext;

	private View mMenu;

	private ListView mListView;

	private int mStatusHeight; // 状态栏高度
	private int mScreenHeight; // 屏幕的高度
	private int mTitleBarHeight; // 标题栏高度

	public PatientPopupWindow(Context context) {
		super();
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenu = inflater.inflate(R.layout.pop_patient_list, null);
		getScreenSize();
		// 设置SelectPicPopupWindow的View
		setContentView(mMenu);
		// 设置SelectPicPopupWindow弹出窗体的宽
		setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		setHeight(mScreenHeight - mStatusHeight - mTitleBarHeight);
		// 设置SelectPicPopupWindow弹出窗体可点击
		setFocusable(true);
		// 设置出入动画
		setAnimationStyle(R.style.pop_patient_list_style);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		setBackgroundDrawable(dw);
		initView();
	}

	/**
	 * 取得屏幕中所需的各项高度
	 */
	private void getScreenSize() {
		mStatusHeight = ScreenUtils.getStatusHeight(mContext);
		mScreenHeight = ScreenUtils.getScreenHeight(mContext);
		mTitleBarHeight = mContext.getResources().getDimensionPixelSize(
				R.dimen.titlebar_height);
	}

	/**
	 * 初始化显示和监听器
	 */
	private void initView() {

		mListView = (ListView) mMenu.findViewById(R.id.lv_patient);

		mMenu.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int width = mMenu.findViewById(R.id.rl_main).getRight();
				int x = (int) event.getX();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (x > width) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

	public ListView getListView() {
		return mListView;
	}
}
