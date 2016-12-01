package com.thr.mobilemedical.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.fragment.BloodoxygenFragment;
import com.thr.mobilemedical.fragment.BloodpressureFragment;
import com.thr.mobilemedical.fragment.BloodsugerFragment;
import com.thr.mobilemedical.fragment.TemperaturepagerFragment;
import com.thr.mobilemedical.utils.ScanUtil;
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;

/**
 * @description NursingrecordActivity.java
 * @date 2015年9月11日 下午2:16:15
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_nursingrecord)
public class NursingrecordActivity extends Activity {

	// 标题栏
	@ViewInject(R.id.titlebar)
	private TitleBar mTitleBar;

	// 左侧菜单
	private LeftMenuPopupWindow mLeftWindow;

	@ViewInject(R.id.ll_tab_temperaturepaper)
	private LinearLayout mTabPaper;
	@ViewInject(R.id.ll_tab_bloodoxygen)
	private LinearLayout mTabOxygenr;
	@ViewInject(R.id.ll_tab_bloodsuger)
	private LinearLayout mTabSuger;
	@ViewInject(R.id.ll_tab_bloodpressure)
	private LinearLayout mTabPressure;

	@ViewInject(R.id.ib_tab_temperature)
	private ImageButton mImgTemperature;
	@ViewInject(R.id.ib_tab_bloodoxygen)
	private ImageButton mImgOxygen;
	@ViewInject(R.id.ib_tab_bloodsuger)
	private ImageButton mImgSuger;
	@ViewInject(R.id.ib_tab_bloodpressure)
	private ImageButton mImgPressure;

	@ViewInject(R.id.tv_temperature)
	private TextView mTextTemperature;
	@ViewInject(R.id.tv_bloodoxygen)
	private TextView mTextOxygen;
	@ViewInject(R.id.tv_bloodsuger)
	private TextView mTextSuger;
	@ViewInject(R.id.tv_bloodpressure)
	private TextView mTextPressure;

	// 4个页面
	private Fragment paperFragment;
	private Fragment oxygenFragment;
	private Fragment sugerFragment;
	private Fragment pressureFragment;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);

		initView();
	}

	private void initView() {

		paperFragment = new TemperaturepagerFragment();
		oxygenFragment = new BloodoxygenFragment();
		sugerFragment = new BloodsugerFragment();
		pressureFragment = new BloodpressureFragment();

		// 将所有的fragment添加到集合中方便后面操作
		fragmentList.add(paperFragment);
		fragmentList.add(oxygenFragment);
		fragmentList.add(sugerFragment);
		fragmentList.add(pressureFragment);

		mTitleBar.setLeftImage(R.drawable.top_list);

		mLeftWindow = new LeftMenuPopupWindow(this);

		mTitleBar.setOnLeftClickListener(new OnLeftClickListener() {

			@Override
			public void onLeftClick(View v) {
				mLeftWindow.showAtLocation(findViewById(R.id.ll_main),
						Gravity.BOTTOM, 0, 0);
			}
		});

		// 最开始显示在基础的状态
		showFragment(fragmentList.get(0));

		// 设置最上层退出处理
		setTopListener(new TopExitImpl());
	}

	/**
	 * 隐藏所有的Fragment
	 * 
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (paperFragment != null) {
			transaction.hide(paperFragment);
		}
		if (oxygenFragment != null) {
			transaction.hide(oxygenFragment);
		}
		if (sugerFragment != null) {
			transaction.hide(sugerFragment);
		}
		if (pressureFragment != null) {
			transaction.hide(pressureFragment);
		}
	}

	/**
	 * 先隐藏所有Fragment，然后显示当前所点击的Fragment
	 * 
	 * @param fragment
	 */
	private void showFragment(Fragment fragment) {
		if (!fragment.isVisible()) {
			FragmentManager manager = getFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			hideFragment(transaction);
			if (!fragment.isAdded()) {
				transaction.add(R.id.fl_content, fragment);
			}
			transaction.show(fragment);
			transaction.commit();
		}
	}

	/**
	 * 重置所有按钮文字状态
	 */
	private void resetBtnState() {
		// 重置图片
		mImgTemperature.setImageResource(R.drawable.bottom_hllr_twd);
		mImgOxygen.setImageResource(R.drawable.bottom_hllr_xy);
		mImgSuger.setImageResource(R.drawable.bottom_hllr_xt);
		mImgPressure.setImageResource(R.drawable.bottom_hllr_xy_09);
		// 重置文字
		mTextTemperature.setTextColor(getResources().getColor(
				R.color.bottom_tab_text));
		mTextOxygen.setTextColor(getResources().getColor(
				R.color.bottom_tab_text));
		mTextSuger.setTextColor(getResources()
				.getColor(R.color.bottom_tab_text));
		mTextPressure.setTextColor(getResources().getColor(
				R.color.bottom_tab_text));
	}

	/**
	 * 跳转至体温单界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_temperaturepaper)
	public void costClick(View v) {
		showFragment(paperFragment);
		resetBtnState();
		mImgTemperature.setImageResource(R.drawable.bottom_hllr_twd_n);
		mTextTemperature.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
	}

	/**
	 * 跳转至血氧界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_bloodoxygen)
	public void eduClick(View v) {
		showFragment(oxygenFragment);
		resetBtnState();
		mImgOxygen.setImageResource(R.drawable.bottom_hllr_xy_n);
		mTextOxygen.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
	}

	/**
	 * 跳转至血糖界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_bloodsuger)
	public void heathClick(View v) {
		showFragment(sugerFragment);
		resetBtnState();
		mImgSuger.setImageResource(R.drawable.bottom_hllr_xt_n);
		mTextSuger.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
	}

	/**
	 * 跳转至血压界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_bloodpressure)
	public void remindClick(View v) {
		showFragment(pressureFragment);
		resetBtnState();
		mImgPressure.setImageResource(R.drawable.bottom_hllr_xy_09_n);
		mTextPressure.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
	}

	private TopActivityListener mTopListener;

	public void setTopListener(TopActivityListener topListener) {
		mTopListener = topListener;
	}

	@Override
	public void onBackPressed() {
		mTopListener.onExit(this);
	}

	@Override
	protected void onResume() {
		ScanUtil.registerScanReceiver(this);
		super.onResume();
	}

}
