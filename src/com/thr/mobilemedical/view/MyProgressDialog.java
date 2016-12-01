package com.thr.mobilemedical.view;

import com.thr.mobilemedical.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @description 自定义等待框
 * @date 2015年9月16日 下午2:23:17
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class MyProgressDialog extends ProgressDialog {

	private AnimationDrawable mAnimation;
	private ImageView mImageView;
	private String mLoadingTip = "";
	private TextView mLoadingTv;
	private int mResid = R.anim.progress_dialog_loading_frame;

	public MyProgressDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(true);
	}

	public MyProgressDialog(Context context, int id) {
		super(context);
		this.mResid = id;
		setCanceledOnTouchOutside(true);
	}

	public MyProgressDialog(Context context, String content, int id) {
		super(context);
		this.mLoadingTip = content;
		this.mResid = id;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {
		setCancelable(false);
		setCanceledOnTouchOutside(false);
		mImageView.setBackgroundResource(mResid);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
		mLoadingTv.setText(mLoadingTip);
	}

	public void setContent(String str) {
		mLoadingTv.setText(str);
	}

	private void initView() {
		setContentView(R.layout.progress_dialog);
		mLoadingTv = (TextView) findViewById(R.id.tv_loadingtext);
		mImageView = (ImageView) findViewById(R.id.iv_loadingimg);
	}

}
