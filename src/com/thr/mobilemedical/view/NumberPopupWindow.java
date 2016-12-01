package com.thr.mobilemedical.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.thr.mobilemedical.R;

/**
 * @description 数字输入弹出框
 * @date 2015-10-8 14:23:48
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class NumberPopupWindow extends PopupWindow {

	private TextView mTextTitle;
	@SuppressWarnings("unused")
	private Context mContext;

	private Button mBtn1;
	private Button mBtn2;
	private Button mBtn3;
	private Button mBtn4;
	private Button mBtn5;
	private Button mBtn6;
	private Button mBtn7;
	private Button mBtn8;
	private Button mBtn9;
	private Button mBtn0;
	private Button mBtnErase;
	private Button mBtnBack;
	private Button mBtnPoint;
	public Button mBtnLast;
	public Button mBtnNext;
	private Button mBtnSave;

	private float min;
	private float max;
	private float min2;
	private float max2;

	private TextView mScreen;

	private View view;

	private SaveClickListener saveClickListener;
	private LastClickListener lastClickListener;
	private NextClickListener nextClickListener;

	private boolean isTemper = false;

	public boolean isTemper() {
		return isTemper;
	}

	public void setTemper(boolean isTemper) {
		this.isTemper = isTemper;
	}

	public Button getSaveButton() {
		return mBtnSave;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin2() {
		return min2;
	}

	public void setMin2(float min2) {
		this.min2 = min2;
	}

	public float getMax2() {
		return max2;
	}

	public void setMax2(float max2) {
		this.max2 = max2;
	}

	public void setSaveClickListener(SaveClickListener saveClickListener) {
		this.saveClickListener = saveClickListener;
	}

	public void setLastClickListener(LastClickListener lastClickListener) {
		this.lastClickListener = lastClickListener;
	}

	public void setNextClickListener(NextClickListener nextClickListener) {
		this.nextClickListener = nextClickListener;
	}

	public interface SaveClickListener {
		void onSaveClick();
	}

	public interface LastClickListener {
		void onLastClick();
	}

	public interface NextClickListener {
		void onNextClick();
	}

	public NumberPopupWindow(Context context) {
		super();
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.pop_number, null);
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

		mScreen = (TextView) view.findViewById(R.id.tv_screen);

		mBtn1 = (Button) view.findViewById(R.id.btn_1);
		mBtn2 = (Button) view.findViewById(R.id.btn_2);
		mBtn3 = (Button) view.findViewById(R.id.btn_3);
		mBtn4 = (Button) view.findViewById(R.id.btn_4);
		mBtn5 = (Button) view.findViewById(R.id.btn_5);
		mBtn6 = (Button) view.findViewById(R.id.btn_6);
		mBtn7 = (Button) view.findViewById(R.id.btn_7);
		mBtn8 = (Button) view.findViewById(R.id.btn_8);
		mBtn9 = (Button) view.findViewById(R.id.btn_9);
		mBtn0 = (Button) view.findViewById(R.id.btn_0);
		mBtnPoint = (Button) view.findViewById(R.id.btn_point);
		mBtnBack = (Button) view.findViewById(R.id.btn_back);
		mBtnSave = (Button) view.findViewById(R.id.btn_save);
		mBtnNext = (Button) view.findViewById(R.id.btn_nextitem);
		mBtnLast = (Button) view.findViewById(R.id.btn_lastitem);
		mBtnErase = (Button) view.findViewById(R.id.btn_erase);

		mBtn1.setOnClickListener(new BtnClickListener());
		mBtn2.setOnClickListener(new BtnClickListener());
		mBtn3.setOnClickListener(new BtnClickListener());
		mBtn4.setOnClickListener(new BtnClickListener());
		mBtn5.setOnClickListener(new BtnClickListener());
		mBtn6.setOnClickListener(new BtnClickListener());
		mBtn7.setOnClickListener(new BtnClickListener());
		mBtn8.setOnClickListener(new BtnClickListener());
		mBtn9.setOnClickListener(new BtnClickListener());
		mBtn0.setOnClickListener(new BtnClickListener());
		mBtnPoint.setOnClickListener(new BtnClickListener());
		mBtnBack.setOnClickListener(new BtnClickListener());
		mBtnSave.setOnClickListener(new BtnClickListener());
		mBtnNext.setOnClickListener(new BtnClickListener());
		mBtnLast.setOnClickListener(new BtnClickListener());
		mBtnErase.setOnClickListener(new BtnClickListener());

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

	class BtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_0:
			case R.id.btn_1:
			case R.id.btn_2:
			case R.id.btn_3:
			case R.id.btn_4:
			case R.id.btn_5:
			case R.id.btn_6:
			case R.id.btn_7:
			case R.id.btn_8:
			case R.id.btn_9:
			case R.id.btn_point:
				int length = mScreen.getText().toString().length();
				// 只允许10位以内的数字
				if (length < 10) {
					if (v.getId() == R.id.btn_point) {
						// 处理有小数点的情况
						if (!mScreen.getText().toString().contains(".")) {
							// 如果长度已经到9了，就不添加小数点了
							if (mScreen.length() == 9) {
								return;
							}
							// 否则添加小数点
							mScreen.setText(mScreen.getText().toString()
									+ ((Button) v).getText().toString());
						}
					} else if (v.getId() == R.id.btn_0) {
						String screen = mScreen.getText().toString();
						// 如果屏幕已经是0了，再点0没有反应；其它情况后面补零
						if ("0".equals(screen)) {
							return;
						} else {
							mScreen.setText(mScreen.getText().toString()
									+ ((Button) v).getText().toString());
						}
					} else {
						String screen = mScreen.getText().toString();
						// 如果屏幕是0的情况，就替换0显示新按的数字
						if ("0".equals(screen)) {
							mScreen.setText(((Button) v).getText().toString());
						} else {
							mScreen.setText(mScreen.getText().toString()
									+ ((Button) v).getText().toString());
						}
					}
				}
				break;
			case R.id.btn_erase:
				String screen = mScreen.getText().toString();
				if (screen.length() == 1) {
					mScreen.setText("0");
				} else {
					mScreen.setText(screen.substring(0, screen.length() - 1));
				}
				break;
			case R.id.btn_save:
				if (saveClickListener != null) {
					saveClickListener.onSaveClick();
				}
				break;
			case R.id.btn_back:
				dismiss();
				break;
			case R.id.btn_lastitem:
				if (lastClickListener != null) {
					lastClickListener.onLastClick();
				}
				break;
			case R.id.btn_nextitem:
				if (nextClickListener != null) {
					nextClickListener.onNextClick();
				}
				break;
			}
		}
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		mTextTitle.setText(title);
	}

	/**
	 * 设置屏幕框显示值
	 * 
	 * @param content
	 */
	public void setScreen(String content) {
		mScreen.setText(content);
	}

	/**
	 * 取得目前正在显示的内容
	 */
	public String getContent() {
		return mScreen.getText().toString();
	}

}
