package com.thr.mobilemedical.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thr.mobilemedical.R;

/**
 * @description 自定义Dialog弹出框（确定、取消按钮）
 * @date 2015年9月30日 上午9:40:13
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class MyDialog extends Dialog {

	private LinearLayout mContent;

	private View mView;

	private TextView mTitle;

	private Button mSure;
	private Button mCancle;

	private SureClickListener mSureClick;

	/**
	 * 确定按钮接口
	 * 
	 * @author Jerry
	 *
	 */
	public interface SureClickListener {
		void onSureClick();
	}

	public MyDialog(Context context, View contentView,
			SureClickListener sureClick) {
		super(context);
		mView = contentView;
		mSureClick = sureClick;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_warning);

		mTitle = (TextView) findViewById(R.id.tv_title);

		mContent = (LinearLayout) findViewById(R.id.ll_content);
		mSure = (Button) findViewById(R.id.btn_sure);
		mCancle = (Button) findViewById(R.id.btn_cancel);

		mContent.addView(mView);
		mSure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSureClick.onSureClick();
				dismiss();
			}
		});
		mCancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		mTitle.setText(title);
	}
}
