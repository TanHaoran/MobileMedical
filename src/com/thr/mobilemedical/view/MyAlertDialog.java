package com.thr.mobilemedical.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.thr.mobilemedical.R;

/**
 * @description 自定义提示弹出框（只有确定按钮）
 * @date 2015-10-9 16:22:10
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class MyAlertDialog extends Dialog {

	private String content;

	private TextView mContent;

	private Button mBtn;

	private SureClickListener mSureClickListener;

	public void setOnSureClickListener(SureClickListener sureClickListener) {
		mSureClickListener = sureClickListener;
	}

	public interface SureClickListener {
		void onSureClick();
	}

	public MyAlertDialog(Context context, String content) {
		super(context);
		this.content = content;
	}

	public MyAlertDialog(Context context, int resId) {
		super(context);
		this.content = context.getResources().getString(resId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_alert);

		mContent = (TextView) findViewById(R.id.tv_content);
		mBtn = (Button) findViewById(R.id.btn);

		mContent.setText(content);

		mBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSureClickListener != null) {
					mSureClickListener.onSureClick();
				}
				dismiss();
			}
		});

	}

	public void setContent(String content) {
		mContent.setText(content);
	}
}
