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
public class SpeedPopupWindow extends PopupWindow {

	private Button mBtn20;
	private Button mBtn30;
	private Button mBtn40;
	private Button mBtn50;
	private Button mBtn60;
	private Button mBtn70;
	private Button mBtn80;
	private Button mBtn90;
	private Button mBtn100;
	private Button mBtnNone;
	private Button mBtnSave;

	private View view;

	private TextView mTextSpeed;
	private String speed;

	private SaveClickListener saveClickListener;

	public void setSaveClickListener(SaveClickListener saveClickListener) {
		this.saveClickListener = saveClickListener;
	}

	public interface SaveClickListener {
		void onSaveClick();
	}

	public SpeedPopupWindow(Context context) {
		super();
	}

	public SpeedPopupWindow(Context context, TextView textSpeed) {
		super();
		mTextSpeed = textSpeed;
		speed = mTextSpeed.getText().toString();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.pop_speed, null);
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

		mBtn20 = (Button) view.findViewById(R.id.btn_20);
		mBtn30 = (Button) view.findViewById(R.id.btn_30);
		mBtn40 = (Button) view.findViewById(R.id.btn_40);
		mBtn50 = (Button) view.findViewById(R.id.btn_50);
		mBtn60 = (Button) view.findViewById(R.id.btn_60);
		mBtn70 = (Button) view.findViewById(R.id.btn_70);
		mBtn80 = (Button) view.findViewById(R.id.btn_80);
		mBtn90 = (Button) view.findViewById(R.id.btn_90);
		mBtn100 = (Button) view.findViewById(R.id.btn_100);
		mBtnNone = (Button) view.findViewById(R.id.btn_none);
		mBtnSave = (Button) view.findViewById(R.id.btn_save);

		mBtn20.setOnClickListener(new BtnClickListener());
		mBtn30.setOnClickListener(new BtnClickListener());
		mBtn40.setOnClickListener(new BtnClickListener());
		mBtn50.setOnClickListener(new BtnClickListener());
		mBtn60.setOnClickListener(new BtnClickListener());
		mBtn70.setOnClickListener(new BtnClickListener());
		mBtn80.setOnClickListener(new BtnClickListener());
		mBtn90.setOnClickListener(new BtnClickListener());
		mBtn100.setOnClickListener(new BtnClickListener());
		mBtnNone.setOnClickListener(new BtnClickListener());
		mBtnSave.setOnClickListener(new BtnClickListener());

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
			case R.id.btn_20:
			case R.id.btn_30:
			case R.id.btn_40:
			case R.id.btn_50:
			case R.id.btn_60:
			case R.id.btn_70:
			case R.id.btn_80:
			case R.id.btn_90:
			case R.id.btn_100:
			case R.id.btn_none:
				if (v.getId() != R.id.btn_none) {
					speed = ((Button) v).getText().toString();
					mTextSpeed.setText(speed);
				} else {
					speed = "0";
					mTextSpeed.setText("0");
				}
				break;
			case R.id.btn_save:
				if (saveClickListener != null) {
					saveClickListener.onSaveClick();
				}
				dismiss();
				break;
			}
		}
	}

	public String getSpeed() {
		return speed;
	}

}
