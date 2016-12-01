package com.thr.mobilemedical.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.view.ChangeheadPopupWindow;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;

/**
 * @description 设置
 * @date 2015-9-15 16:42:06
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_setting)
public class SettingAcitivity extends Activity {

	@ViewInject(R.id.titlebar)
	private TitleBar mTitleBar;

	@ViewInject(R.id.tv_name)
	private TextView mTextName;
	@ViewInject(R.id.tv_office)
	private TextView mTextOffice;

	@ViewInject(R.id.iv_head)
	private ImageView mHead;

	// 修改头像弹出框
	private ChangeheadPopupWindow mPopWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = LayoutParams.MATCH_PARENT;
		lp.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(lp);
		initView();
	}

	private void initView() {
		mTitleBar.setLeftImage(R.drawable.top_fh);
		// 点击左侧返回按钮
		mTitleBar.setOnLeftClickListener(new OnLeftClickListener() {

			@Override
			public void onLeftClick(View v) {
				finish();
			}
		});
		mTextName.setText(LoginInfo.USERNAME);
		mTextOffice.setText(LoginInfo.OFFICE_NAME);

		mPopWindow = new ChangeheadPopupWindow(this);
	}

	@OnClick(R.id.iv_head)
	public void headClick(View v) {
		mPopWindow.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM,
				0, 0);
	}

}
